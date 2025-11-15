package com.nobru.api_ai.api.tools;

import com.nobru.api_ai.api.domain.Barber;
import com.nobru.api_ai.api.service.BarberService;
import com.nobru.api_ai.api.service.BookService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BarberTools {

    private final BookService bookService;
    private final BarberService barberService;

    public BarberTools(BookService bookService, BarberService barberService) {
        this.bookService = bookService;
        this.barberService = barberService;
    }

    @Tool(description = "Lista todos os barbeiros disponíveis na barbearia")
    public List<String> listarBarbeiros() {
        return barberService.getAllBarbers()
                .stream()
                .map(Barber::getName)
                .toList();
    }

    @Tool(description = "Lista os horários disponíveis de um barbeiro específico")
    public List<String> listarHorarios(String barbeiroId) {
        // Chamada à sua API com barbeiroId
        return List.of("09:00", "10:00", "11:30");
    }

    @Tool(description = "Lista os cortes oferecidos pela barbearia")
    public List<String> listarCortes() {
        // Chamada à sua API real
        return List.of("Degradê", "Social", "Máquina 1", "Barba completa");
    }

//    @Tool(description = "Agenda um corte com um barbeiro, em um horário e com um tipo de corte específico")
//    public BookResponse agendarCorte(Book request) {
//        return bookService.book(request);
//    }
}
