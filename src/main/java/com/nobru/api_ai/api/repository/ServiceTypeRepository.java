package com.nobru.api_ai.api.repository;

import com.nobru.api_ai.api.domain.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {
}
