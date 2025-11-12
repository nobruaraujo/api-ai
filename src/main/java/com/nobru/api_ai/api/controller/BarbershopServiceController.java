package com.nobru.api_ai.api.controller;

import com.nobru.api_ai.api.domain.BarbershopServices;
import com.nobru.api_ai.api.service.BarbershopServicesService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/barbershop-service")
public class BarbershopServiceController {

    private final BarbershopServicesService service;

    public BarbershopServiceController(BarbershopServicesService service) {
        this.service = service;
    }

    @PostMapping
    public BarbershopServices createService(@RequestBody BarbershopServices request) {
        return service.createService(request);
    }

    @GetMapping("/{id}")
    public BarbershopServices getService(@PathVariable Long id) {
        return service.getServiceById(id);
    }
}
