package com.nobru.api_ai.api.service;

import com.nobru.api_ai.api.domain.Barber;
import com.nobru.api_ai.api.domain.Booking;
import com.nobru.api_ai.api.domain.ServiceType;
import com.nobru.api_ai.api.domain.dto.BookingRequest;
import com.nobru.api_ai.api.domain.response.BookingResponse;
import com.nobru.api_ai.api.domain.schedule.Schedule;
import com.nobru.api_ai.api.domain.schedule.TimeSlot;
import com.nobru.api_ai.api.exception.BookingNotFoundException;
import com.nobru.api_ai.api.exception.TimeSlotUnavailableException;
import com.nobru.api_ai.api.mapper.BookingMapper;
import com.nobru.api_ai.api.repository.BookingRepository;
import com.nobru.api_ai.api.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ScheduleRepository scheduleRepository;
    private final BarberService barberService;
    private final ServiceTypeService serviceTypeService;
    private final BookingMapper bookingMapper;

    @Transactional
    public BookingResponse createBooking(BookingRequest request) {
        Barber barber = barberService.getBarberById(request.barberId());
        ServiceType serviceType = serviceTypeService.getServiceById(request.serviceTypeId());

        Schedule schedule = scheduleRepository.findByBarberIdAndDate(barber.getId(), request.date())
                .orElseThrow(() -> new IllegalArgumentException("Barbeiro não disponível no dia informado"));

        TimeSlot slot = schedule.getTimeSlots().stream()
                .filter(ts -> ts.getStartTime().equals(request.time()))
                .findFirst()
                .orElseThrow(() -> new TimeSlotUnavailableException("Horário não encontrado", HttpStatus.NOT_FOUND));

        if (!slot.isAvailable()) {
            throw new TimeSlotUnavailableException("Horário não disponível", HttpStatus.CONFLICT);
        }

        slot.book();
        scheduleRepository.save(schedule);

        try {
            Booking booking = Booking.builder()
                    .barberId(barber.getId())
                    .date(request.date())
                    .time(request.time())
                    .serviceType(serviceType)
                    .build();
            bookingRepository.save(booking);
            return bookingMapper.toResponse(booking);
        } catch (Exception e) {
            throw new TimeSlotUnavailableException(String.format("Erro inesperado: %s", e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public BookingResponse getBookById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException(id));
        return bookingMapper.toResponse(booking);
    }
}
