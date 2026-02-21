package com.nobru.api_ai.api.exception;

import org.springframework.http.HttpStatus;

import java.util.Set;

public class ServiceTypeNotFoundException extends DomainException {
    public ServiceTypeNotFoundException(Long id) {
        super(String.format("Serviço com ID %d não encontrado", id), HttpStatus.NOT_FOUND);
    }

    public ServiceTypeNotFoundException(Set<Long> missingIds) {
        super(String.format("Serviços não encontrados: %s", missingIds), HttpStatus.NOT_FOUND);
    }
}