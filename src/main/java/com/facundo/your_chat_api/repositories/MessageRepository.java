package com.facundo.your_chat_api.repositories;

import com.facundo.your_chat_api.dto.MessageResponse;
import com.facundo.your_chat_api.entities.Chat;
import com.facundo.your_chat_api.entities.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT new com.facundo.your_chat_api.dto.MessageResponse" +
            "(m.id, m.text, m.type, m.creationDate) " +
            "FROM Message m WHERE m.chat = :chat")
    Page<MessageResponse> findByChat(Chat chat, Pageable pageable);
}
