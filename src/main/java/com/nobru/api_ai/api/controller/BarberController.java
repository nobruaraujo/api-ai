package com.nobru.api_ai.api.controller;

import com.nobru.api_ai.api.domain.Barber;
import com.nobru.api_ai.api.domain.dto.BarberRequest;
import com.nobru.api_ai.api.service.BarberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/barbers")
public class BarberController {

    private final BarberService barberService;

    public BarberController(BarberService barberService) {
        this.barberService = barberService;
    }

    @PostMapping
    public ResponseEntity<Barber> createBarber(@Valid @RequestBody BarberRequest request) {
        Barber barber = barberService.createBarber(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(barber);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Barber> getBarber(@PathVariable Long id) {
        Barber barber = barberService.getBarberById(id);
        return ResponseEntity.ok(barber);
    }
}
