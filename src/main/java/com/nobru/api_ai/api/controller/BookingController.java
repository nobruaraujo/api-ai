package com.nobru.api_ai.api.controller;

import com.nobru.api_ai.api.domain.Booking;
import com.nobru.api_ai.api.domain.dto.BookingRequest;
import com.nobru.api_ai.api.domain.response.BookingResponse;
import com.nobru.api_ai.api.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
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
