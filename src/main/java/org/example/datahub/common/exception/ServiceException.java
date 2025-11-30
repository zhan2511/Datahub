package org.example.datahub.common.exception;


import org.springframework.http.HttpStatus;


public class ServiceException extends RuntimeException {
    private final String errorCode;
    private final HttpStatus httpStatus;

    public ServiceException(String errorCode, String message, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }


}
