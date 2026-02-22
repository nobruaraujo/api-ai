package com.nobru.api_ai.api.controller;

import com.nobru.api_ai.api.domain.ServiceType;
import com.nobru.api_ai.api.domain.dto.ServiceTypeRequest;
import com.nobru.api_ai.api.service.ServiceTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.nobru.api_ai.api.utils.URLMapping.ROOT_API_SERVICE_TYPE;

@RestController
@RequestMapping(ROOT_API_SERVICE_TYPE)
public class ServiceTypeController {

    private final ServiceTypeService service;

    public ServiceTypeController(ServiceTypeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ServiceType> createService(@RequestBody ServiceTypeRequest request) {
        ServiceType barbershopService = service.createService(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(barbershopService);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceType> getService(@PathVariable Long id) {
        ServiceType barbershopService = service.getServiceById(id);
        return ResponseEntity.ok(barbershopService);
    }
}
