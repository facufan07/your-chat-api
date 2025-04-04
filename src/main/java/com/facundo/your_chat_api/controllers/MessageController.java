package com.facundo.your_chat_api.controllers;

import com.facundo.your_chat_api.dto.CreateMessageRequest;
import com.facundo.your_chat_api.dto.MessageResponse;
import com.facundo.your_chat_api.services.entitiesService.message.IMessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
@CrossOrigin(
        origins = {"http://localhost:3000", "https://your-chat-hazel.vercel.app"}
)
public class MessageController {

    private final IMessageService messageService;

    public MessageController(IMessageService messageService) {
        this.messageService = messageService;
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/{chatId}")
    public ResponseEntity<Page<MessageResponse>> getAllMessages(@RequestParam(defaultValue = "0") int page,
                                                                @PathVariable Long chatId) {

        Pageable pageable = PageRequest.of(
                page,
                10,
                Sort.by(Sort.Order.asc("id"))
        );

        Page<MessageResponse> messages = this.messageService.getAllMessages(chatId, pageable);

        return ResponseEntity.ok(messages);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping
    public ResponseEntity<MessageResponse> createMessage(@RequestBody CreateMessageRequest request) {

        MessageResponse messageResponse = this.messageService.createMessage(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
    }
}
