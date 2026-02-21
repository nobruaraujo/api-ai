package com.nobru.api_ai.api.domain.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public record AddAvailabilityRequest(

        @JsonProperty("period")
        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,

        @JsonProperty("startTime")
        @NotNull
        @JsonFormat(pattern = "HH:mm")
        LocalTime startTime,

        @JsonProperty("endTime")
        @NotNull
        @JsonFormat(pattern = "HH:mm")
        LocalTime endTime
) {}
