package com.chols.fu.error;

import org.springframework.http.HttpStatus;

public interface IErrorCode {
    String getMessage();
    HttpStatus getHttpStatus();
}
