package com.nobru.api_ai.api.controller;

import com.nobru.api_ai.api.domain.Book;
import com.nobru.api_ai.api.domain.BookResponse;
import com.nobru.api_ai.api.service.BookService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public BookResponse book(@RequestBody Book bookRequest) {
        return bookService.book(bookRequest);
    }

    @GetMapping("/{id}")
    public Book findBook(@PathVariable Long id) {
        return bookService.getBarberById(id);
    }
}
