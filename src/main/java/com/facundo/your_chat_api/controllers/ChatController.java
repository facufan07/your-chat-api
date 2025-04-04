package com.facundo.your_chat_api.controllers;

import com.facundo.your_chat_api.dto.ChatResponse;
import com.facundo.your_chat_api.dto.CreateChatRequest;
import com.facundo.your_chat_api.dto.UpdateChatRequest;
import com.facundo.your_chat_api.services.entitiesService.chat.IChatService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
@CrossOrigin(
        origins = {"http://localhost:3000", "https://your-chat-hazel.vercel.app"}
)
public class ChatController {

    private final IChatService chatService;


    public ChatController(IChatService chatService) {
        this.chatService = chatService;
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping
    public ResponseEntity<Page<ChatResponse>> getAllChats
                                        (@RequestParam(defaultValue = "0")
                                        int page){

        Pageable pageable = PageRequest.of(
                page,
                10,
                Sort.by(Sort.Order.desc("lastMessageDate"))
        );

        Page<ChatResponse> chats = this.chatService.getAllChats(pageable);

        if (chats.hasContent()){
            return ResponseEntity.ok(chats);
        }

        return ResponseEntity.notFound().build();

    }

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping
    public ResponseEntity<ChatResponse> createChat(@RequestBody CreateChatRequest request) {
        ChatResponse chatResponse = this.chatService.createChat(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(chatResponse);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ChatResponse> deleteChat(@PathVariable Long id) {

        ChatResponse chatResponse = this.chatService.deleteChat(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(chatResponse);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping
    public ResponseEntity<ChatResponse> updateChatName(@RequestBody UpdateChatRequest request) {

        ChatResponse chatResponse = this.chatService.updateChatName(request);

        return ResponseEntity.ok(chatResponse);

    }

}
