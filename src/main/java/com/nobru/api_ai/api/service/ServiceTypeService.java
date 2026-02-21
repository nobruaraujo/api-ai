package com.nobru.api_ai.api.service;

import com.nobru.api_ai.api.domain.ServiceType;
import com.nobru.api_ai.api.domain.dto.ServiceTypeRequest;
import com.nobru.api_ai.api.exception.ServiceTypeNotFoundException;
import com.nobru.api_ai.api.repository.ServiceTypeRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServiceTypeService {

    private final ServiceTypeRepository repository;

    public ServiceTypeService(ServiceTypeRepository serviceTypeRepository) {
        this.repository = serviceTypeRepository;
    }

    public ServiceType createService(ServiceTypeRequest request) {
        return repository.save(new ServiceType(request.name(), request.price()));
    }

    public ServiceType getServiceById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ServiceTypeNotFoundException(id));
    }

    public Set<ServiceType> getServicesByIds(Set<Long> idServices) {
        List<ServiceType> services = repository.findAllById(idServices);
        validateServiceType(idServices, services);
        return new HashSet<>(services);
    }

    private static void validateServiceType(Set<Long> idServices, List<ServiceType> services) {
        if (services.size() != idServices.size()) {
            Set<Long> foundIds = services.stream()
                    .map(ServiceType::getId)
                    .collect(Collectors.toSet());

            Set<Long> missingIds = idServices.stream()
                    .filter(id -> !foundIds.contains(id))
                    .collect(Collectors.toSet());

            throw new ServiceTypeNotFoundException(missingIds);
        }
    }

    public List<ServiceType> getAllServices() {
        return repository.findAll();
    }
}
