package com.nobru.api_ai.api.domain.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record AvailableTimesResponse(
        LocalDate date,
        List<LocalTime> availableTimes
) {}

