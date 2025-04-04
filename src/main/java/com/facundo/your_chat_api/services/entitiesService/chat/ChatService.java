package com.facundo.your_chat_api.services.entitiesService.chat;

import com.facundo.your_chat_api.dto.ChatResponse;
import com.facundo.your_chat_api.dto.CreateChatRequest;
import com.facundo.your_chat_api.dto.UpdateChatRequest;
import com.facundo.your_chat_api.entities.Chat;
import com.facundo.your_chat_api.entities.User;
import com.facundo.your_chat_api.exception.ObjectNotFoundException;
import com.facundo.your_chat_api.exception.UnauthorizedException;
import com.facundo.your_chat_api.repositories.ChatRepository;
import com.facundo.your_chat_api.services.auth.IAuthService;
import com.facundo.your_chat_api.services.entitiesService.user.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ChatService implements IChatService {

    private final IUserService userService;

    private final ChatRepository chatRepository;

    private final IAuthService authService;

    public ChatService(IUserService userService,
                       ChatRepository chatRepository,
                       IAuthService authService) {
        this.userService = userService;
        this.chatRepository = chatRepository;
        this.authService = authService;
    }

    @Override
    public Page<ChatResponse> getAllChats(Pageable pageable) {
        User user = this.authService.getUser();

        Page<ChatResponse> chats = this.chatRepository.findByUser(user, pageable);

        return chats;
    }

    @Override
    public ChatResponse createChat(CreateChatRequest request) {
        User user = this.authService.getUser();

        Chat chat = new Chat();
        chat.setName(request.getName());
        chat.setCreationDate(LocalDateTime.now());
        chat.setUser(user);
        chat.setLastMessageDate(LocalDateTime.now());
        Chat chatBD = this.chatRepository.save(chat);

        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setId(chatBD.getId());
        chatResponse.setCreationDate(chatBD.getCreationDate());
        chatResponse.setName(chatBD.getName());
        chatResponse.setLastMessageDate(chatBD.getLastMessageDate());

        return chatResponse;
    }

    @Override
    public ChatResponse deleteChat(Long id) {
        User user = this.authService.getUser();

        Chat chat = this.chatRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Chat not found with id: " + id));

        if(!chat.getUser().getId().equals(user.getId())){
            throw new UnauthorizedException("You are not authorized to delete this chat");
        }

        this.chatRepository.delete(chat);

        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setId(chat.getId());
        chatResponse.setCreationDate(chat.getCreationDate());
        chatResponse.setName(chat.getName());
        chatResponse.setLastMessageDate(chat.getLastMessageDate());

        return chatResponse;
    }

    @Override
    public ChatResponse updateChatName(UpdateChatRequest request) {
        User user = this.authService.getUser();

        Chat chat = this.chatRepository.findById(request.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Chat not found with id: " + request.getId()));

        if(!chat.getUser().getId().equals(user.getId())){
            throw new UnauthorizedException("You are not authorized to change the name of this chat");
        }

        chat.setName(request.getNewName());
        Chat chatBD = this.chatRepository.save(chat);

        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setId(chatBD.getId());
        chatResponse.setCreationDate(chatBD.getCreationDate());
        chatResponse.setName(chatBD.getName());
        chatResponse.setLastMessageDate(chatBD.getLastMessageDate());

        return chatResponse;
    }

}
