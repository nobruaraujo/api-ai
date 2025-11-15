package com.nobru.api_ai.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.Set;

public record BookRequest(
   @JsonProperty("idBarber")
   @NotNull
   Long idBarber,

   @JsonProperty("idServices")
   @NotNull
   Set<Long> idServices
) {}
