package com.husqvarna.todo.bl.handler;

import com.husqvarna.todo.bl.common.ErrorCode;
import com.husqvarna.todo.bl.common.MessageConstants;
import com.husqvarna.todo.bl.exception.BusinessException;
import com.husqvarna.todo.bl.vo.ErrorVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TodoExceptionHandler {

    // TODO - add loggers

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorVO> handleBusinessException(BusinessException ex) {
        ErrorVO errorVO = ErrorVO.builder()
                .errorCode(ex.getErrorCode())
                .errorMessage(ex.getErrorMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorVO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorVO> handleException(Exception ex) {
        ErrorVO errorVO = ErrorVO.builder()
                .errorCode(ErrorCode.SERVER_ERROR.name())
                .errorMessage(MessageConstants.SERVER_ERROR)
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorVO);
    }
}
