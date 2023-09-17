package com.husqvarna.todo.bl.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TodoVO {
    private Long id;
    private String name;
    private String status;
}
