package com.nobru.api_ai.api.controller;

import com.nobru.api_ai.api.domain.Barber;
import com.nobru.api_ai.api.domain.dto.BarberRequest;
import com.nobru.api_ai.api.service.BarberService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/barber")
public class BarberController {

    private final BarberService barberService;

    public BarberController(BarberService barberService) {
        this.barberService = barberService;
    }

    @PostMapping
    public Barber createBarber(@RequestBody BarberRequest request) {
        return barberService.createBarber(request);
    }

    @GetMapping("/{id}")
    public Barber getBarber(@PathVariable Long id) {
        return barberService.getBarberById(id);
    }
}
