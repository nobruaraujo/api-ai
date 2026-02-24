package com.nobru.api_ai.whatsapp.gateway;

import com.nobru.api_ai.whatsapp.domain.dto.IncomingWhatsAppMessage;

public interface ChatAIProcessor {
    String processMessage(IncomingWhatsAppMessage incomingWhatsAppMessage);
}
