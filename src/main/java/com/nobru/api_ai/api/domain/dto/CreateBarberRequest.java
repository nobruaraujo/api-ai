package com.nobru.api_ai.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record CreateBarberRequest(
        @JsonProperty("name")
        @NotBlank()
        String name
) {}
