package com.facundo.your_chat_api.services.entitiesService.chat;

import com.facundo.your_chat_api.dto.ChatResponse;
import com.facundo.your_chat_api.dto.CreateChatRequest;
import com.facundo.your_chat_api.dto.UpdateChatRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IChatService {
    Page<ChatResponse> getAllChats(Pageable pageable);

    ChatResponse createChat(CreateChatRequest request);

    ChatResponse deleteChat(Long id);

    ChatResponse updateChatName(UpdateChatRequest request);
}
