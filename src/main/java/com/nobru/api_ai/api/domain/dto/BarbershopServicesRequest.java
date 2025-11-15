package com.nobru.api_ai.api.domain.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public record BarbershopServicesRequest(
        @JsonProperty("name")
        @NotNull
        String name,

        @JsonProperty("price")
        @NotNull
        BigDecimal price
) {
}
