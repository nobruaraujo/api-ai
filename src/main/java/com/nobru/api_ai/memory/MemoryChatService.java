package com.nobru.api_ai.memory;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MemoryChatService {

    private final ChatClient chatClient;
    private final MemoryChatRepository memoryChatRepository;

    public MemoryChatService(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory, MemoryChatRepository memoryChatRepository) {
        this.memoryChatRepository = memoryChatRepository;
        this.chatClient = chatClientBuilder
                .defaultAdvisors(
                    MessageChatMemoryAdvisor.builder(chatMemory).build()
                )
                .build();
    }

    String sendMessageToOpenAI(String message, String chatId) {
        return this.chatClient.prompt()
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, chatId))
                .user(message)
                .call()
                .content();
    }

    record ChatResponse(String chatId, String response) {}

    public ChatResponse createChatSession(String message) {
        String chatId = memoryChatRepository.generateChatId(UUID.randomUUID().toString());
        String response = sendMessageToOpenAI(message, chatId);
        return new ChatResponse(chatId, response);
    }
}
