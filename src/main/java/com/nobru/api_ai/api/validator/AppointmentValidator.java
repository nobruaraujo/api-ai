package com.nobru.api_ai.api.validator;

import com.nobru.api_ai.api.domain.Barber;
import com.nobru.api_ai.api.domain.BarbershopServices;
import com.nobru.api_ai.api.domain.Book;
import com.nobru.api_ai.api.domain.BookResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class AppointmentValidator {

    public static final String AGENDAMENTO_REALIZADO_COM_SUCESSO = "Agendamento realizado com sucesso";
    public static final String ERRO_AO_REALIZAR_AGENDAMENTO = "Erro ao realizar agendamento";
    private boolean isValid;
    private List<String> errorMessages;
    private BookResponse bookResponse;

    public BookResponse validate(Book book) {
        //validateBarber(book.getBarber());
        //validateService(book.getBarberServices());
        //validateAppointmentTime(book.getTime());

        return bookResponse;
    }

    private void validateBarber(Barber barber) {
        if (barber == null) {
            this.isValid = false;
        }
    }

    private void validateService(Set<BarbershopServices> services) {
        if (services == null) {
            throw new IllegalArgumentException("Serviço não pode ser nulo");
        }
    }

    public void validateAppointmentTime(LocalDateTime time) {
        if (time.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Horário do agendamento não pode ser no passado");
        }
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        this.isValid = valid;
    }
}
