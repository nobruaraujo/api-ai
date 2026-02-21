package com.nobru.api_ai.api.exception;

import org.springframework.http.HttpStatus;

public class BarberAvailabilityIllegalArgumentException extends DomainException {
    public BarberAvailabilityIllegalArgumentException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
