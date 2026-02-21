package com.nobru.api_ai.api.domain.schedule;

import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class AvailableTimesCalculator {

    public List<LocalTime> calculate(Schedule schedule) {
        return schedule.getAvailableTimes();
    }
}
