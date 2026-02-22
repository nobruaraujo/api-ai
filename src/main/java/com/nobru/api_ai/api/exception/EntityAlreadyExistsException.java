package com.nobru.api_ai.api.exception;

import org.springframework.http.HttpStatus;

public class EntityAlreadyExistsException extends DomainException {
    public EntityAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
