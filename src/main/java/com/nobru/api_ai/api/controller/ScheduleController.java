package com.nobru.api_ai.api.controller;

import com.nobru.api_ai.api.domain.response.AvailableTimesResponse;
import com.nobru.api_ai.api.domain.schedule.Schedule;
import com.nobru.api_ai.api.dto.CreateScheduleRequest;
import com.nobru.api_ai.api.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.nobru.api_ai.api.utils.URLMapping.ROOT_API_SCHEDULES;

@RestController
@RequestMapping(ROOT_API_SCHEDULES)
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/barbers/{barberId}")
    public AvailableTimesResponse getAvailableTimes(@PathVariable Long barberId, @RequestParam LocalDate date) {
        List<LocalTime> times = scheduleService.getAvailableTimes(barberId, date);
        return new AvailableTimesResponse(date, times);
    }

    @PostMapping("/barbers/{barberId}")
    public Schedule createSchedule(@PathVariable Long barberId, @RequestBody CreateScheduleRequest request) {
        return scheduleService.createSchedule(barberId, request);
    }
}
