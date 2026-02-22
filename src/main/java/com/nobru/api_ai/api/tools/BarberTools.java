package com.nobru.api_ai.api.tools;

import com.nobru.api_ai.api.domain.Barber;
import com.nobru.api_ai.api.domain.dto.BookingRequest;
import com.nobru.api_ai.api.domain.response.BookingResponse;
import com.nobru.api_ai.api.service.BarberService;
import com.nobru.api_ai.api.service.ScheduleService;
import com.nobru.api_ai.api.service.ServiceTypeService;
import com.nobru.api_ai.api.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class BarberTools {

    private final BookingService bookingService;
    private final BarberService barberService;
    private final ServiceTypeService barbershopServices;
    private final ScheduleService scheduleService;

    @Tool(description = "Lista todos os barbeiros disponíveis na barbearia.")
    public List<String> listarBarbeiros() {
        log.info("Listando barbeiros disponíveis");
        List<String> barbers = barberService.getAllBarbers()
                .stream()
                .map(Barber::getName)
                .toList();
        log.info("Barbeiros encontrados: {}", barbers);
        return barbers;
    }

    @Tool(description = "Busca o Id de um barbeiro pelo nome.")
    public Long buscarBarbeiroPorNome(String name) {
        log.info("Buscando barbeiro pelo nome: {}", name.toUpperCase());
        Barber barber = barberService.getBarberByName(name.toUpperCase());
        log.info("Barbeiro encontrado: {} com ID: {}", barber.getName(), barber.getId());
        return barber.getId();
    }

    @Tool(description = "Lista os horários disponíveis de um barbeiro específico.")
    public List<LocalTime> listarHorarios(
            @ToolParam(description = "O identificador único (ID) do barbeiro. Obtenha este ID usando a ferramenta buscarBarbeiroPorNome")
            Long barberId,
            LocalDate date) {
        Barber barber = barberService.getBarberById(barberId);
        log.info("Listando horários disponíveis de hoje ({}) para o barbeiro com ID: {}", date, barber.getId());
        List<LocalTime> availableTimes = scheduleService.getAvailableTimes(barber.getId(), date);
        log.info("Horários disponíveis encontrados: {}", availableTimes);
        return availableTimes;
    }

    @Tool(description = "Lista os cortes oferecidos pela barbearia")
    public List<String> listarCortes() {
        log.info("Listando cortes oferecidos pela barbearia");
        List<String> services = barbershopServices.getAllServices()
                .stream()
                .map(service -> service.getName() + " - R$ " + service.getPrice())
                .toList();
        log.info("Cortes encontrados: {}", services);
        return services;
    }

    @Tool(description = "Agenda um corte com um barbeiro, em um horário e com um tipo de corte específico")
    public BookingResponse agendarCorte(BookingRequest request) {
        log.info("Agendando corte para o barbeiro com ID: {} no horário: {}", request.barberId(), request.time());
        return bookingService.createBooking(request);
    }
}
