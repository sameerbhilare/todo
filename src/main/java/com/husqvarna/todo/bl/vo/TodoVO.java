package com.husqvarna.todo.bl.vo;

import com.husqvarna.todo.bl.common.TodoStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TodoVO {
    private Long id;
    private String name;
    private TodoStatus status;
}
