package com.husqvarna.todo.bl.service;

import com.husqvarna.todo.bl.common.ErrorCode;
import com.husqvarna.todo.bl.common.MessageConstants;
import com.husqvarna.todo.bl.common.TodoStatus;
import com.husqvarna.todo.bl.dataaccess.entity.Todo;
import com.husqvarna.todo.bl.dataaccess.repository.TodoRepository;
import com.husqvarna.todo.bl.exception.BusinessException;
import com.husqvarna.todo.bl.vo.TodoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@Slf4j
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public List<TodoVO> getTodos(String status) {

        TodoStatus todoStatus = TodoStatus.fromValue(status);
        if (status != null && todoStatus == null) {
            log.error("getTodos() => Received invalid status={}", status);
            throw new BusinessException(ErrorCode.INVALID_STATUS.name(), MessageConstants.INVALID_STATUS);
        }

        // fetch todos from database either based on status or all (if status is not provided input)
        List<Todo> fetchedTodos = Optional.ofNullable(status)
                .map(s -> todoRepository.findByStatus(todoStatus))
                .orElse(todoRepository.findAll());

        // map JPA entity to value object
        return Optional.of(fetchedTodos)
                .filter(CollectionUtils::isNotEmpty)
                .orElse(Collections.emptyList())
                .stream()
                .map(mapToDoVO)
                .toList();
    }

    public TodoVO getTodo(Long todoId) {

        return todoRepository.findById(todoId)
                .map(mapToDoVO)
                .orElseThrow(() -> {
                    log.error("getTodo() => Todo with ID {} not found.", todoId);
                    return new BusinessException(ErrorCode.TASK_NOT_FOUND.name(), MessageConstants.TASK_NOT_FOUND);
                });
    }

    @Transactional()
    public void deleteTodo(Long todoId) {

        // if todoId not present, throw business exception
        validateIfTodoPresent(todoId);

        // delete task
        todoRepository.deleteById(todoId);
    }

    @Transactional()
    public void updateTodo(Long todoId, TodoVO todoVO) {

        // if todoId not present, throw business exception
        Todo todoToBeUpdated = validateIfTodoPresent(todoId);

        TodoStatus updatedStatus = TodoStatus.fromValue(todoVO.getStatus());
        if (todoVO.getStatus() != null && updatedStatus == null) {
            log.error("updateTodo() => Received invalid status={} for todoId={}", todoVO.getStatus(), todoId);
            throw new BusinessException(ErrorCode.INVALID_STATUS.name(), MessageConstants.INVALID_STATUS);
        }

        // update the task
        todoToBeUpdated.setName(todoVO.getName());
        todoToBeUpdated.setStatus(updatedStatus);

        // save updated task in the db
        todoRepository.save(todoToBeUpdated);
    }

    @Transactional()
    public void deleteAllTodos() {
        todoRepository.deleteAll();
    }

    @Transactional()
    public TodoVO createTodo(TodoVO todoVO) {

        // create Todo_ entity
        Todo todo = new Todo();
        todo.setName(todoVO.getName());
        todo.setStatus(TodoStatus.ACTIVE);

        // save in the db and return mapped object
        return Optional.of(todoRepository.save(todo))
                .map(mapToDoVO)
                .orElseThrow(() -> {
                    log.error("createTodo() => Unable to create a Todo {}", todo);
                    return new BusinessException(ErrorCode.UNABLE_TO_CREATE.name(), MessageConstants.UNABLE_TO_CREATE);
                });
    }

    private Todo validateIfTodoPresent(Long todoId) {
        // if todoId not present, throw business exception
        return todoRepository.findById(todoId)
                .orElseThrow(() -> {
                    log.error("validateIfTodoPresent() => Todo with ID {} not found.", todoId);
                    return new BusinessException(ErrorCode.TASK_NOT_FOUND.name(), MessageConstants.TASK_NOT_FOUND);
                });
    }

    /**
     * Mapper function to convert Todo_ DB entity to TodoVO POJO
     */
    private static final Function<Todo, TodoVO> mapToDoVO = todo -> TodoVO.builder()
            .id(todo.getId())
            .name(todo.getName())
            .status(todo.getStatus().name())
            .build();

}
