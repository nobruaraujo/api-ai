package com.nobru.api_ai.api.exception;

import org.springframework.http.HttpStatus;

public class InvalidScheduleDateException extends DomainException {
    public InvalidScheduleDateException(String message) {
        super(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
