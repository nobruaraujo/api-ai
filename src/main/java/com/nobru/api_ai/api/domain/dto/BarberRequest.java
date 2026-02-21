package com.nobru.api_ai.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record BarberRequest (
        @JsonProperty("name")
        @NotBlank()
        String name
) {}
