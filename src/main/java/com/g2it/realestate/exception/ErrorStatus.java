package com.g2it.realestate.exception;

import org.springframework.http.HttpStatus;

public enum ErrorStatus {
	NOT_FOUND("NOT_FOUND", HttpStatus.NOT_FOUND),
    INVALID_HEADER("INVALID_TOKEN", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("INVALID_TOKEN", HttpStatus.UNAUTHORIZED),
    INVALID_LOGIN_DETAILS("INVALID_LOGIN_DETAILS", HttpStatus.UNAUTHORIZED),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR),
    EMPLOYEE_NOT_FOUND("EMPLOYEE_NOT_FOUND", HttpStatus.NOT_FOUND),
    DUPLICATE_RECORD("DUPLICATE_RECORD", HttpStatus.BAD_REQUEST),
    NOT_ACCEPTABLE("NOT_ACCEPTABLE", HttpStatus.NOT_ACCEPTABLE),
    INVALID_REQUEST("INVALID_REQUEST", HttpStatus.BAD_REQUEST);

    String code;
    HttpStatus httpStatus;

    ErrorStatus(String code, HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }
}
