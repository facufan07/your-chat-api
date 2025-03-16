package com.facundo.your_chat_api.controllers;

import com.facundo.your_chat_api.dto.MessageResponse;
import com.facundo.your_chat_api.services.entitiesService.message.IMessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final IMessageService messageService;

    public MessageController(IMessageService messageService) {
        this.messageService = messageService;
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping
    public ResponseEntity<Page<MessageResponse>> getAllMessages(@RequestParam(defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(
                page,
                10
        );

        Page<MessageResponse> messages = this.messageService.getAllMessages(pageable);

        return ResponseEntity.ok(messageResponse);
    }
}
