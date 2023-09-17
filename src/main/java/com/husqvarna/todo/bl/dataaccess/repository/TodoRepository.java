package com.husqvarna.todo.bl.dataaccess.repository;

import com.husqvarna.todo.bl.common.TodoStatus;
import com.husqvarna.todo.bl.dataaccess.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    public List<Todo> findByStatus(TodoStatus status);
}
