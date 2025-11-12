package com.nobru.api_ai.api.service;

import com.nobru.api_ai.api.domain.Book;
import com.nobru.api_ai.api.domain.BookResponse;
import com.nobru.api_ai.api.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookResponse book(Book request) {

        /*
        AppointmentValidator validator = new AppointmentValidator();
        validator.validate(request);

        if (!validator.isValid()) {
            return new BookResponse("Erro ao realizar agendamento", true, null);
        }

         */

        bookRepository.save(request);


        return new BookResponse("Agendamento realizado com sucesso", false, null);
    }

    public Book getBarberById(Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            throw new NullPointerException("Agendamento não encontrado");
        }
        return book;
    }
}
