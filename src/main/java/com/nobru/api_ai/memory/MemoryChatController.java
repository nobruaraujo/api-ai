package com.nobru.api_ai.memory;

import com.nobru.api_ai.chat.ChatMessageResponse;
import com.nobru.api_ai.chat.Chat;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class MemoryChatController {

    private final MemoryChatService memoryChatService;

    public MemoryChatController(MemoryChatService memoryChatService) {
        this.memoryChatService = memoryChatService;
    }

    @PostMapping("/send")
    public ChatMessageResponse sendMessageToOpenAI(@RequestBody Chat chat) {
        String response = memoryChatService.sendMessageToOpenAI(chat.phoneNumber(), chat.message());
        return new ChatMessageResponse(response);
    }
}
