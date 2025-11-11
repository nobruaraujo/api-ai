package com.nobru.api_ai.api.domain;

import java.util.List;

public record BookResponse(
        String message,
        boolean hasError,
        List<String> errorMessages
) {}
