package com.husqvarna.todo.bl.service;

import com.husqvarna.todo.bl.common.ErrorCode;
import com.husqvarna.todo.bl.common.MessageConstants;
import com.husqvarna.todo.bl.common.TodoStatus;
import com.husqvarna.todo.bl.db.entity.Todo;
import com.husqvarna.todo.bl.db.repository.TodoRepository;
import com.husqvarna.todo.bl.exception.BusinessException;
import com.husqvarna.todo.bl.vo.TodoVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public List<TodoVO> getTodos(String status) {

        TodoStatus todoStatus = TodoStatus.fromValue(status);
        if (status != null && todoStatus == null) {
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

    private static final Function<Todo, TodoVO> mapToDoVO = todo -> TodoVO.builder()
            .id(todo.getId())
            .name(todo.getName())
            .status(todo.getStatus())
            .build();
}
