package com.nobru.api_ai.whatsapp.usecase;

import com.nobru.api_ai.whatsapp.domain.dto.IncomingWhatsAppMessage;
import com.nobru.api_ai.whatsapp.gateway.ChatAIProcessor;
import com.nobru.api_ai.whatsapp.gateway.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class ProcessMessageWhatsApp {

    private final ChatAIProcessor chatAIProcessor;
    private final MessageSender messageSender;

    public void execute(Map<String, String> params) {
        String userPhone = params.get("From").replace("whatsapp:+", "");
        String body = params.get("Body");

        String response = chatAIProcessor.processMessage(new IncomingWhatsAppMessage(userPhone, body));
        messageSender.sendWhatsAppMessage(userPhone, response);
    }
}
