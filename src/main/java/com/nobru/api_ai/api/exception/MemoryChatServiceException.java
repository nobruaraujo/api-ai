package com.nobru.api_ai.api.exception;

import org.springframework.http.HttpStatus;

public class MemoryChatServiceException extends DomainException {
    public MemoryChatServiceException(String s) {
        super(s, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
