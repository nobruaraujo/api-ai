package com.nobru.api_ai.api.exception;

import org.springframework.http.HttpStatus;

public class BarberNotFoundException extends DomainException {
    public BarberNotFoundException(Long id) {
        super(String.format("Barbeiro com ID %d não encontrado", id), HttpStatus.NOT_FOUND);
    }
}
