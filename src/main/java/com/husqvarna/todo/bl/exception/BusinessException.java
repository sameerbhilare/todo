package com.husqvarna.todo.bl.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BusinessException extends RuntimeException {

    private String errorCode;
    private String errorMessage;
}
