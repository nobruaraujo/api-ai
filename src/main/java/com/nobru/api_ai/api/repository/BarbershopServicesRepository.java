package com.nobru.api_ai.api.repository;

import com.nobru.api_ai.api.domain.BarbershopServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarbershopServicesRepository extends JpaRepository<BarbershopServices, Long> {
}
