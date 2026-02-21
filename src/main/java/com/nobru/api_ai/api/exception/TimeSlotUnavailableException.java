package com.nobru.api_ai.api.exception;

import org.springframework.http.HttpStatus;

public class TimeSlotUnavailableException extends DomainException {
    public TimeSlotUnavailableException(String message, HttpStatus status) {
        super(message, status);
    }
}
