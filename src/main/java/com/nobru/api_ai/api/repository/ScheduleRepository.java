package com.nobru.api_ai.api.repository;

import com.nobru.api_ai.api.domain.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByBarberIdAndDateBetween(
            Long barberId,
            LocalDate from,
            LocalDate to
    );

    Optional<Schedule> findByBarberIdAndDate(Long barberId, LocalDate date);
}