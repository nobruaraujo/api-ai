package com.nobru.api_ai.api.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public record AvailabilitySlot(
        Long availabilityId,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime
) {}
