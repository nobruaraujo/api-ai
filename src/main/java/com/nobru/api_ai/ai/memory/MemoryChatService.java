package com.nobru.api_ai.ai.memory;

import com.nobru.api_ai.api.service.BookService;
import com.nobru.api_ai.api.tools.BarberTools;
import com.nobru.api_ai.ai.persona.PersonaConfig;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;

@Service
public class MemoryChatService {

    private final ChatClient chatClient;
    private final BookService bookService;

    public MemoryChatService(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory, BookService bookService) {
        this.bookService = bookService;
        this.chatClient = chatClientBuilder
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .defaultSystem(PersonaConfig.SYSTEM_PERSONA)
                //.defaultTools(new BarberTools(bookService))
                .build();
    }

    String sendMessageToOpenAI(String phoneNumber, String message) {
        return this.chatClient.prompt()
                //.options(ChatOptions.builder().temperature(0.8).maxTokens(25).build()) // TODO -> Check average token usage
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, phoneNumber))
                .user(message)
                .call()
                .content();
    }
}
