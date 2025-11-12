package com.nobru.api_ai.api.service;

import com.nobru.api_ai.api.domain.BarbershopServices;
import com.nobru.api_ai.api.repository.BarbershopServicesRepository;
import org.springframework.stereotype.Service;

@Service
public class BarbershopServicesService {

    private final BarbershopServicesRepository repository;

    public BarbershopServicesService(BarbershopServicesRepository barbershopServicesRepository) {
        this.repository = barbershopServicesRepository;
    }

    public BarbershopServices createService(BarbershopServices request) {
        return repository.save(request);
    }

    public BarbershopServices getServiceById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NullPointerException("Serviço não encontrado"));
    }
}
