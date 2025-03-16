package com.facundo.your_chat_api.controllers;

import com.facundo.your_chat_api.dto.MessageResponse;
import com.facundo.your_chat_api.services.entitiesService.message.IMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final IMessageService messageService;

    public MessageController(IMessageService messageService) {
        this.messageService = messageService;
    }

    public ResponseEntity<MessageResponse> getAllMessages() {

        MessageResponse messageResponse = this.messageService.getAllMessages();

        return null;
    }
}
