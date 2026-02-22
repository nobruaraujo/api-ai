package com.nobru.api_ai.ai.memory;

import com.nobru.api_ai.ai.chat.ChatMessageResponse;
import com.nobru.api_ai.ai.chat.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.nobru.api_ai.api.utils.URLMapping.ROOT_API_MEMORY_CHAT;

@RestController
@RequiredArgsConstructor
@RequestMapping(ROOT_API_MEMORY_CHAT)
public class MemoryChatController {

    private final MemoryChatService memoryChatService;

    @PostMapping("/send")
    public ChatMessageResponse sendMessageToOpenAI(@RequestBody Chat chat) {
        String response = memoryChatService.sendMessageToOpenAI(chat.phoneNumber(), chat.message());
        return new ChatMessageResponse(response);
    }
}
