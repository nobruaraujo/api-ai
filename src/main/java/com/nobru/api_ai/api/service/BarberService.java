package com.nobru.api_ai.api.service;

import com.nobru.api_ai.api.domain.Barber;
import com.nobru.api_ai.api.repository.BarberRepository;
import org.springframework.stereotype.Service;

@Service
public class BarberService {

    private final BarberRepository barberRepository;

    public BarberService(BarberRepository barberRepository) {
        this.barberRepository = barberRepository;
    }

    public Barber createBarber(Barber barberRequest) {
        return barberRepository.save(barberRequest);
    }

    public Barber getBarberById(Long id) {
        return barberRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("Barbeiro não encontrado"));
    }
}
