package com.nobru.api_ai.memory;

import com.nobru.api_ai.domain.ChatMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat/memory")
public class MemoryChatController {

    private final MemoryChatService memoryChatService;

    public MemoryChatController(MemoryChatService memoryChatService) {
        this.memoryChatService = memoryChatService;
    }

    @PostMapping
    ChatMessage sendMessageToOpenAI(ChatMessage chatMessage) {
        String response = memoryChatService.sendMessageToOpenAI(chatMessage.message());
        return new ChatMessage(response);
    }
}
