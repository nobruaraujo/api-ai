package com.nobru.api_ai.api.mapper;

import com.nobru.api_ai.api.domain.ServiceType;
import com.nobru.api_ai.api.domain.response.ServiceTypeResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceTypeMapper {
    ServiceTypeResponse toResponse(ServiceType serviceType);
}

