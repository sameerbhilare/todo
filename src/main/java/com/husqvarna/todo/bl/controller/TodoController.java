package com.husqvarna.todo.bl.controller;

import com.husqvarna.todo.bl.service.TodoService;
import com.husqvarna.todo.bl.vo.TodoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("")
    public List<TodoVO> getTodos(@RequestParam(name = "status", required = false) String status) {

        return todoService.getTodos(status);
    }

}
