package com.nobru.api_ai.api.service;

import com.nobru.api_ai.api.domain.schedule.AvailableTimesCalculator;
import com.nobru.api_ai.api.domain.schedule.Schedule;
import com.nobru.api_ai.api.dto.CreateScheduleRequest;
import com.nobru.api_ai.api.exception.InvalidScheduleDateException;
import com.nobru.api_ai.api.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final AvailableTimesCalculator calculator;
    private final BarberService barberService;

    public List<LocalTime> getAvailableTimes(Long barberId, LocalDate date) {
        Optional<Schedule> schedule = scheduleRepository
                .findByBarberIdAndDate(barberId, date);

        if (schedule.isEmpty()) return List.of();

        return calculator.calculate(schedule.get());
    }

    @Transactional
    public Schedule createSchedule(Long barberId, CreateScheduleRequest request) {
        if (request.date().isBefore(LocalDate.now())) throw new InvalidScheduleDateException("A data do agendamento não pode ser anterior à data atual");
        if (request.date().isEqual(LocalDate.now()) && request.slots().stream().anyMatch(d -> d.isBefore(LocalTime.now()))) throw new InvalidScheduleDateException("Os horários do agendamento não podem ser anteriores à hora atual");

        barberService.getBarberById(barberId);
        Schedule schedule = Schedule.create(barberId, request.date(), request.slots());
        return scheduleRepository.save(schedule);
    }

}
