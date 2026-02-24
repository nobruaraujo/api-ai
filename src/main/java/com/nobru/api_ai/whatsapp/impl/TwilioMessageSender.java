package com.nobru.api_ai.whatsapp.impl;

import com.nobru.api_ai.whatsapp.gateway.MessageSender;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TwilioMessageSender implements MessageSender {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.whatsapp.number}")
    private String twilioNumber;

    @Override
    public void sendWhatsAppMessage(String userNumber, String messageBody) {
        Twilio.init(accountSid, authToken);
        Message message = Message.creator(
                        new PhoneNumber(toTwilioFormat(userNumber)),
                        new PhoneNumber(twilioNumber),
                        messageBody)
                .create();

        log.info("Mensagem enviada com sucesso: {}", message.getSid());
    }

    public String toTwilioFormat(String phoneNumber) {
        return "whatsapp:+" + phoneNumber;
    }
}