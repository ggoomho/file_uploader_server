package com.chols.fu.error;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode implements IErrorCode {
    
    // 404, NOT FOUND
    PROGRESS_NOT_FOUND(HttpStatus.NOT_FOUND, "Progress not found.");
    
    private final HttpStatus httpStatus;
    private final String msg;

    ErrorCode(HttpStatus httpStatus, String msg) {
        this.httpStatus = httpStatus;
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
