package com.nobru.api_ai.api.domain.response;

public record BookingResponse(
   Long id,
   String customerName,
   ServiceTypeResponse serviceType,
   String scheduledDateTime,
   String status
) {}
