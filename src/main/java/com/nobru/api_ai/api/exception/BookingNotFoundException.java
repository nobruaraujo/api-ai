package com.nobru.api_ai.api.exception;

import org.springframework.http.HttpStatus;

public class BookingNotFoundException extends DomainException {
    public BookingNotFoundException(Long id) {
        super(String.format("Agendamento com ID %d não encontrado", id), HttpStatus.NOT_FOUND);
    }
}
