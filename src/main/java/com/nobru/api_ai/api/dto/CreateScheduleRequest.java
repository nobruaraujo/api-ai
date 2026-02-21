package com.nobru.api_ai.api.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record CreateScheduleRequest(
   LocalDate date,
   List<LocalTime> slots
) {}
