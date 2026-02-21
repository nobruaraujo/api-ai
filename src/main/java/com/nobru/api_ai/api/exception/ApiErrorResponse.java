package com.nobru.api_ai.api.exception;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ApiErrorResponse(
        String status,
        String message,
        LocalDateTime timestamp
) {
}
