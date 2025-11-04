package com.nobru.api_ai.barber.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity
public class BarberService {

    @Id
    private Long id;
    private String name;
    private BigDecimal price;
}
