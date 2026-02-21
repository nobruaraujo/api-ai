package com.nobru.api_ai.api.domain.response;

import java.math.BigDecimal;

public record ServiceTypeResponse(
    Long id,
    String name,
    BigDecimal price
) {}
