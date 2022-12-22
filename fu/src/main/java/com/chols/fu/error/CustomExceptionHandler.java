package com.chols.fu.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ResponseBody
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> ExceptionHandler(CustomException ex) {
        ErrorResponse response = ErrorResponse.of(ex.getErrorCode());
        return ResponseEntity.status(ex.getErrorCode().getHttpStatus()).body(response);
    }
}
