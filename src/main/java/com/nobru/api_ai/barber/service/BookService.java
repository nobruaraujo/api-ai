package com.nobru.api_ai.barber.service;

import com.nobru.api_ai.barber.domain.Book;
import com.nobru.api_ai.barber.domain.BookResponse;
import com.nobru.api_ai.barber.repository.BookRepository;
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
}
