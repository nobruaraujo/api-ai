package com.nobru.api_ai.whatsapp;

import com.nobru.api_ai.whatsapp.usecase.ProcessMessageWhatsApp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TwilioWebhookController {

    private final ProcessMessageWhatsApp processMessageWhatsApp;

    @PostMapping(value = "/whatsapp/callback", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Object> receiveWhatsApp(@RequestParam Map<String, String> params) {
        log.info("WhatsApp Message received");

        processMessageWhatsApp.execute(params);

        return ResponseEntity.ok().build();
    }
}
