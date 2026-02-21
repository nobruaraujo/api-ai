package com.nobru.api_ai.api.service;

import com.nobru.api_ai.api.domain.schedule.AvailableTimesCalculator;
import com.nobru.api_ai.api.domain.schedule.Schedule;
import com.nobru.api_ai.api.dto.CreateScheduleRequest;
import com.nobru.api_ai.api.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final AvailableTimesCalculator calculator;

    public ScheduleService(ScheduleRepository scheduleRepository, AvailableTimesCalculator calculator) {
        this.scheduleRepository = scheduleRepository;
        this.calculator = calculator;
    }

    public List<LocalTime> getAvailableTimes(Long barberId, LocalDate date) {
        Schedule schedule = scheduleRepository
                .findByBarberIdAndDate(barberId, date)
                .orElseThrow(() -> new IllegalArgumentException("Agenda não encontrada para o barbeiro na data especificada"));

        return calculator.calculate(schedule);
    }

    @Transactional
    public Schedule createSchedule(Long barberId, CreateScheduleRequest request) {
        Schedule schedule = Schedule.create(barberId, request.date(), request.slots());
        return scheduleRepository.save(schedule);
    }

}
