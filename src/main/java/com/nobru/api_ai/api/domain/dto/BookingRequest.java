package com.nobru.api_ai.api.domain.dto;

import com.nobru.api_ai.api.domain.ServiceType;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

public record BookingRequest(
        @NotNull
        Long barberId,

        @NotNull
        LocalDate date,

        @NotNull
        LocalTime time,

        @NotNull
        Long serviceTypeId
) {
}
