package com.nobru.api_ai.api.service;

import com.nobru.api_ai.api.domain.Barber;
import com.nobru.api_ai.api.domain.dto.CreateBarberRequest;
import com.nobru.api_ai.api.exception.EntityAlreadyExistsException;
import com.nobru.api_ai.api.exception.BarberNotFoundException;
import com.nobru.api_ai.api.repository.BarberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BarberService {

    private final BarberRepository barberRepository;

    public Barber createBarber(CreateBarberRequest request) {
        log.info("Creating barber with name: {}", request.name());
        String name = request.name().toUpperCase();

        if (barberRepository.existsByName(name)) {
            log.error("Barber with name {} already exists.", name);
            throw new EntityAlreadyExistsException(String.format("Barber with name %s already exists.", name));
        }

        return barberRepository.save(new Barber(name));
    }

    public Barber getBarberById(Long id) {
        log.info("Retrieving barber with id: {}", id);
        return barberRepository.findById(id)
                .orElseThrow(() -> new BarberNotFoundException(id));
    }

    public List<Barber> getAllBarbers() {
        return barberRepository.findAll();
    }

    public Barber getBarberByName(String name) {
        return barberRepository.findByName(name).orElseThrow(() -> new BarberNotFoundException(name));
    }
}
