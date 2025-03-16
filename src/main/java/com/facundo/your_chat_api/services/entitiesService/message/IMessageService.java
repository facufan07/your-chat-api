package com.facundo.your_chat_api.services.entitiesService.message;

import com.facundo.your_chat_api.dto.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IMessageService {
    Page<MessageResponse> getAllMessages(Pageable pageable);
}
