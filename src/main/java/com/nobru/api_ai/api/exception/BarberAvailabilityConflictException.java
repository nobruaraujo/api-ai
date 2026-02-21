package com.nobru.api_ai.api.exception;

import org.springframework.http.HttpStatus;

public class BarberAvailabilityConflictException extends DomainException {
    public BarberAvailabilityConflictException() {
        super("O barbeiro já possui um expediente cadastrado para o período especificado.", HttpStatus.CONFLICT);
    }
}
