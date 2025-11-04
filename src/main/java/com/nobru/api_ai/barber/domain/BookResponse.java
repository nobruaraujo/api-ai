package com.nobru.api_ai.barber.domain;

import java.util.List;

public record BookResponse(
        String message,
        boolean hasError,
        List<String> errorMessages
) {}
