package com.facundo.your_chat_api.services.entitiesService.message;

import com.facundo.your_chat_api.dto.MessageResponse;
import com.facundo.your_chat_api.entities.Message;
import com.facundo.your_chat_api.entities.User;
import com.facundo.your_chat_api.repositories.MessageRepository;
import com.facundo.your_chat_api.services.auth.IAuthService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageService implements IMessageService {

    private final IAuthService authService;

    private final MessageRepository messageRepository;

    public MessageService(IAuthService authService,
                          MessageRepository messageRepository) {
        this.authService = authService;
        this.messageRepository = messageRepository;
    }

    @Override
    public Page<MessageResponse> getAllMessages(Pageable pageable) {
        User user = this.authService.getUser();

        Page<MessageResponse> messages = this.messageRepository.findByChat();

        return null;
    }
}
