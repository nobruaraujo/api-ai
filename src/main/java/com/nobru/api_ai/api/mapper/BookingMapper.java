package com.nobru.api_ai.api.mapper;

import com.nobru.api_ai.api.domain.Booking;
import com.nobru.api_ai.api.domain.response.BookingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring", uses = {ServiceTypeMapper.class})
public interface BookingMapper {

    @Mapping(target = "customerName", expression = "java((String) null)")
    @Mapping(target = "scheduledDateTime", expression = "java(formatDateTime(booking))")
    @Mapping(target = "status", constant = "SCHEDULED")
    BookingResponse toResponse(Booking booking);

    default String formatDateTime(Booking booking) {
        if (booking == null || booking.getDate() == null || booking.getTime() == null) return null;
        return booking.getDate().atTime(booking.getTime()).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
