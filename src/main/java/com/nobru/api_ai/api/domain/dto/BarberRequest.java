package com.nobru.api_ai.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public record BarberRequest (
        @JsonProperty("name")
        @NotNull
        String name
) {}
