package com.nobru.api_ai.api.service;

import com.nobru.api_ai.api.domain.Barber;
import com.nobru.api_ai.api.domain.BarbershopServices;
import com.nobru.api_ai.api.domain.Book;
import com.nobru.api_ai.api.domain.BookResponse;
import com.nobru.api_ai.api.domain.dto.BookRequest;
import com.nobru.api_ai.api.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BarberService barberService;
    private final BarbershopServicesService barbershopServicesService;

    public BookService(BookRepository bookRepository, BarberService barberService, BarbershopServicesService barbershopServicesService) {
        this.bookRepository = bookRepository;
        this.barberService = barberService;
        this.barbershopServicesService = barbershopServicesService;
    }

    public BookResponse book(BookRequest request) {
        Barber barber = barberService.getBarberById(request.idBarber());
        Set<BarbershopServices> services = barbershopServicesService.getServicesByIds(request.idServices());

        bookRepository.save(new Book(barber, services));

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
