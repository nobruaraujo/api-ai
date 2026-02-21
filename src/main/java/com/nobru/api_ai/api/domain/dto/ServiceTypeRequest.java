package com.nobru.api_ai.api.domain.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ServiceTypeRequest(
        @JsonProperty("name")
        @NotBlank
        String name,

        @JsonProperty("price")
        @NotNull
        BigDecimal price
) {
}
