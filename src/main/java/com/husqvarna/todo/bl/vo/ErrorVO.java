package com.husqvarna.todo.bl.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorVO {

    private String errorCode;
    private String errorMessage;
}
