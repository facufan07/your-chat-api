package com.facundo.your_chat_api.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;

    private LocalDateTime creationDate;

    @OneToMany(mappedBy = "chat")
    private List<Message> messages;

    private LocalDateTime lastMessageDate;

}
