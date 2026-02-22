package com.nobru.api_ai.api.controller;

import com.nobru.api_ai.api.domain.dto.BookingRequest;
import com.nobru.api_ai.api.domain.response.BookingResponse;
import com.nobru.api_ai.api.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.nobru.api_ai.api.utils.URLMapping.ROOT_API_BOOKINGS;

@RestController
@RequestMapping(ROOT_API_BOOKINGS)
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest request) {
        BookingResponse booking = bookingService.createBooking(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> findBooking(@PathVariable Long id) {
        BookingResponse booking = bookingService.getBookById(id);
        return ResponseEntity.ok(booking);
    }
}
