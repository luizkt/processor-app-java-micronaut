package br.com.lkt.exception;


import io.micronaut.http.HttpStatus;

public class BusinessException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String message;

    public BusinessException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
