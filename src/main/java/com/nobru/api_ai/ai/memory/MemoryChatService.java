package com.nobru.api_ai.ai.memory;

import com.nobru.api_ai.ai.persona.PersonaConfig;
import com.nobru.api_ai.api.exception.MemoryChatServiceException;
import com.nobru.api_ai.api.service.BarberService;
import com.nobru.api_ai.api.service.BookingService;
import com.nobru.api_ai.api.service.ScheduleService;
import com.nobru.api_ai.api.service.ServiceTypeService;
import com.nobru.api_ai.api.tools.BarberTools;
import com.nobru.api_ai.whatsapp.domain.dto.IncomingWhatsAppMessage;
import com.nobru.api_ai.whatsapp.gateway.ChatAIProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;

import static com.nobru.api_ai.ai.persona.PersonaConfig.SYSTEM_PROMPT;

@Slf4j
@Service
public class MemoryChatService implements ChatAIProcessor {

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

    @Override
    public String processMessage(IncomingWhatsAppMessage incomingWhatsAppMessage) {
        return this.sendMessageToOpenAI(incomingWhatsAppMessage.userWhatsAppPhone(), incomingWhatsAppMessage.body());
    }

    public String sendMessageToOpenAI(String phoneNumber, String message) {
        try {
            log.info("Processing message for phone number {}: {}", phoneNumber, message);
            return this.chatClient.prompt()
                    .system(SYSTEM_PROMPT)
                    .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, phoneNumber))
                    .user(message)
                    .call()
                    .content();
        } catch (Exception e) {
            log.error("Error processing message for phone number {}: {}", phoneNumber, e.getMessage(), e);
            throw new MemoryChatServiceException("Desculpe, ocorreu um erro ao processar sua mensagem. Por favor, tente novamente mais tarde.");
        }
    }
}
