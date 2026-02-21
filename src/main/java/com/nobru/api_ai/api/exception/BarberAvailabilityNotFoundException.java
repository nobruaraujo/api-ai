package com.nobru.api_ai.api.exception;

import org.springframework.http.HttpStatus;

public class BarberAvailabilityNotFoundException extends DomainException {
    public BarberAvailabilityNotFoundException(Long id) {
        super(String.format("Disponibilidade não encontrada para o Barbeiro com ID %d", id), HttpStatus.NOT_FOUND);
    }
}
