package com.husqvarna.todo.bl.handler;

import com.husqvarna.todo.bl.common.ErrorCode;
import com.husqvarna.todo.bl.common.MessageConstants;
import com.husqvarna.todo.bl.exception.BusinessException;
import com.husqvarna.todo.bl.vo.ErrorVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class TodoExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorVO> handleBusinessException(BusinessException ex) {
        log.error("Business Exception occurred.", ex);
        ErrorVO errorVO = ErrorVO.builder()
                .errorCode(ex.getErrorCode())
                .errorMessage(ex.getErrorMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorVO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorVO> handleException(Exception ex) {
        log.error("Generic Exception occurred.", ex);
        ErrorVO errorVO = ErrorVO.builder()
                .errorCode(ErrorCode.SERVER_ERROR.name())
                .errorMessage(MessageConstants.SERVER_ERROR)
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorVO);
    }
}
