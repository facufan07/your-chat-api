package com.facundo.your_chat_api.repositories;

import com.facundo.your_chat_api.dto.ChatResponse;
import com.facundo.your_chat_api.entities.Chat;
import com.facundo.your_chat_api.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query("SELECT new com.facundo.your_chat_api.dto.ChatResponse" +
            "(c.id, c.name, c.creationDate, c.lastMessageDate) " +
            "FROM Chat c WHERE c.user = :user")
    Page<ChatResponse> findByUser(User user, Pageable pageable);

    Optional<Chat> findByName(String oldName);
}
