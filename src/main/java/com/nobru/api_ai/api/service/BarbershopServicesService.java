package com.nobru.api_ai.api.service;

import com.nobru.api_ai.api.domain.BarbershopServices;
import com.nobru.api_ai.api.domain.dto.BarbershopServicesRequest;
import com.nobru.api_ai.api.repository.BarbershopServicesRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class BarbershopServicesService {

    private final BarbershopServicesRepository repository;

    public BarbershopServicesService(BarbershopServicesRepository barbershopServicesRepository) {
        this.repository = barbershopServicesRepository;
    }

    public BarbershopServices createService(BarbershopServicesRequest request) {
        return repository.save(new BarbershopServices(request.name(), request.price()));
    }

    public BarbershopServices getServiceById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NullPointerException("Serviço não encontrado"));
    }

    public Set<BarbershopServices> getServicesByIds(Set<Long> idServices) {
        return new HashSet<>(repository.findAllById(idServices));
    }
}
