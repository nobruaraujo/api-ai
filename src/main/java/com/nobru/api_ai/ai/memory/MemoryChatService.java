package com.nobru.api_ai.ai.memory;

import com.nobru.api_ai.ai.persona.PersonaConfig;
import com.nobru.api_ai.api.service.BarberService;
import com.nobru.api_ai.api.service.BookingService;
import com.nobru.api_ai.api.service.ScheduleService;
import com.nobru.api_ai.api.service.ServiceTypeService;
import com.nobru.api_ai.api.tools.BarberTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Service;

@Service
public class MemoryChatService {

    private final ChatClient chatClient;
    private final BookingService bookingService;
    private final BarberService barberService;
    private final ServiceTypeService serviceTypeService;
    private final ScheduleService scheduleService;

    public MemoryChatService(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory, BookingService bookingService, BarberService barberService, ServiceTypeService serviceTypeService, ScheduleService scheduleService) {
        this.bookingService = bookingService;
        this.barberService = barberService;
        this.serviceTypeService = serviceTypeService;
        this.scheduleService = scheduleService;
        this.chatClient = chatClientBuilder
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .defaultSystem(PersonaConfig.SYSTEM_PERSONA)
                .defaultTools(new BarberTools(bookingService, barberService, serviceTypeService, scheduleService))
                .build();
    }

    String sendMessageToOpenAI(String phoneNumber, String message) {
        //Message instruction1 = new SystemMessage("Procure sempre responder de forma educada e prestativa. Utilize as ferramentas disponíveis para fornecer informações precisas sobre os serviços da barbearia, horários disponíveis e agendamento de cortes. Se o usuário solicitar algo que não esteja relacionado aos serviços da barbearia, responda educadamente que você está aqui para ajudar com informações sobre a barbearia e seus serviços.");
        //List<Message> messages = List.of(instruction1);
        return this.chatClient.prompt()
                //.options(ChatOptions.builder().temperature(0.1)/*.maxTokens(25)*/.build()) // TODO -> Check average token usage
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, phoneNumber))
                //.tools(new BarberTools(bookingService, barberService, serviceTypeService, scheduleService))
                .user(message)
                .call()
                .content();
    }
}
