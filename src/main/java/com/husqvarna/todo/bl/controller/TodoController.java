package com.husqvarna.todo.bl.controller;

import com.husqvarna.todo.bl.service.TodoService;
import com.husqvarna.todo.bl.vo.TodoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Entrypoint controller class for all Todo_ related APIs
 */
@RestController
@RequestMapping("/api/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    /**
     * GET API to fetch All Todos or Todos based on status.
     *
     * @param status - Optional request parameter. If not present, returns all Todos.
     *               Allowed values are ACTIVE, COMPLETED.     *
     * @return list of applicable Todos
     */
    @GetMapping("")
    public List<TodoVO> getTodos(@RequestParam(name = "status", required = false) String status) {

        return todoService.getTodos(status);
    }


    /**
     * GET API to fetch single Todo_ details.
     *
     * @param todoId - Id of the todo_ item
     * @return single Todo_ item.
     */
    @GetMapping("/{id}")
    public TodoVO getTodo(@PathVariable(name = "id", required = true) Long todoId) {
        return todoService.getTodo(todoId);
    }


    /**
     * API to create a Todo_. The created todo_ will be in ACTIVE status.
     *
     * @param todoVO - contains updated details about the Todo_ - name and/or status.
     *               TodoVO.id and TodoVO.status are not considered.
     */
    @PostMapping("")
    public ResponseEntity<TodoVO> createTodo(@RequestBody TodoVO todoVO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(todoService.createTodo(todoVO));
    }


    /**
     * API to update a Todo_
     *
     * @param todoId - ID of the Todo_ whose details like name or status to be updated.
     * @param todoVO - contains updated details about the Todo_ - name and/or status.
     *               TodoVO.id is optional and is not considered.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateTodo(@PathVariable(name = "id", required = true) Long todoId,
                                             @RequestBody TodoVO todoVO) {

        todoService.updateTodo(todoId, todoVO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /**
     * API to delete a Todo_
     *
     * @param todoId - ID of the Todo_ to be deleted.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTodo(@PathVariable(name = "id", required = true) Long todoId) {

        todoService.deleteTodo(todoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * API to delete all Todos.
     */
    @DeleteMapping("")
    public ResponseEntity<HttpStatus> purgeAllTodos() {
        todoService.deleteAllTodos();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
