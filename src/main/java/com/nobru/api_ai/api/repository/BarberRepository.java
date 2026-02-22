package com.nobru.api_ai.api.repository;

import com.nobru.api_ai.api.domain.Barber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BarberRepository extends JpaRepository<Barber, Long> {
    boolean existsByName(String name);
    Optional<Barber> findByName(String name);
}
