package com.nobru.api_ai.api.domain.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

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
