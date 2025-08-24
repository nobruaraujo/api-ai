package com.nobru.api_ai.memory;

import com.nobru.api_ai.domain.ChatMessage;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat/memory")
public class MemoryChatController {

    private final MemoryChatService memoryChatService;

    public MemoryChatController(MemoryChatService memoryChatService) {
        this.memoryChatService = memoryChatService;
    }

    @PostMapping("/{chatId}")
    ChatMessage sendMessageToOpenAI(@PathVariable String chatId, @RequestBody ChatMessage chatMessage) {
        String response = memoryChatService.sendMessageToOpenAI(chatMessage.message(), chatId);
        return new ChatMessage(response);
    }

    @PostMapping("/new" )
    MemoryChatService.ChatResponse createChatSession(String message) {
        return this.memoryChatService.createChatSession(message);
    }
}
