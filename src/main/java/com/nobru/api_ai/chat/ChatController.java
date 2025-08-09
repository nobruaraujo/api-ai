package com.nobru.api_ai.chat;

import com.nobru.api_ai.domain.ChatMessage;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @PostMapping("/send")
    String generation(@RequestBody ChatMessage chatMessage) {
        return this.chatClient.prompt()
                .user(chatMessage.message())
                .call()
                .content();
    }
}