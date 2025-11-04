package com.nobru.api_ai.barber.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Barber {
    @Id
    private Long id;
    private String name;
}
