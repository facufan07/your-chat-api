package com.facundo.your_chat_api.repositories;

import com.facundo.your_chat_api.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
