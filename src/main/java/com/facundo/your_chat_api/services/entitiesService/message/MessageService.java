package com.facundo.your_chat_api.services.entitiesService.message;

import com.facundo.your_chat_api.dto.CreateMessageRequest;
import com.facundo.your_chat_api.dto.MessageResponse;
import com.facundo.your_chat_api.entities.Chat;
import com.facundo.your_chat_api.entities.Message;
import com.facundo.your_chat_api.entities.User;
import com.facundo.your_chat_api.exception.ObjectNotFoundException;
import com.facundo.your_chat_api.exception.UnauthorizedException;
import com.facundo.your_chat_api.repositories.ChatRepository;
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

    private final ChatRepository chatRepository;

    public MessageService(IAuthService authService,
                          MessageRepository messageRepository,
                          ChatRepository chatRepository) {
        this.authService = authService;
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
    }

    @Override
    public Page<MessageResponse> getAllMessages( Long chatId, Pageable pageable) {
        User user = this.authService.getUser();
        Chat chat = this.chatRepository.findById(chatId)
                .orElseThrow(() -> new ObjectNotFoundException("Chat not found with id: " + chatId));
        if(!chat.getUser().getId().equals(user.getId())){
            throw new UnauthorizedException("You are not authorized to access this chat");
        }

        Page<MessageResponse> messages = this.messageRepository.findByChat(chat, pageable);

        return messages;
    }

    @Override
    public MessageResponse createMessage(CreateMessageRequest request) {
        User user = this.authService.getUser();
        Chat chat = this.chatRepository.findById(request.getChatId())
                .orElseThrow(() -> new ObjectNotFoundException("Chat not found with id: " + request.getChatId()));

        if(!chat.getUser().getId().equals(user.getId())){
            throw new UnauthorizedException("You are not authorized to access this chat");
        }
        Message message = new Message();
        message.setChat(chat);
        message.setText(request.getMessage());
        message.setType(request.getType());
        message.setCreationDate(LocalDateTime.now());
        Message messageBD = this.messageRepository.save(message);

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setId(messageBD.getId());
        messageResponse.setText(messageBD.getText());
        messageResponse.setType(messageBD.getType());
        messageResponse.setCreationDate(messageBD.getCreationDate());

        return messageResponse;
    }
}
