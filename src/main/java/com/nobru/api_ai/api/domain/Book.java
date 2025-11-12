package com.nobru.api_ai.api.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "barber_id")
    private Barber barber;

    @ManyToMany
    private Set<BarbershopServices> barbershopServices;

    private LocalDateTime time;

//    @PrePersist
//    public void generateId() {
//        if (id == null) {
//            id = UUID.randomUUID();
//        }
//    }
}