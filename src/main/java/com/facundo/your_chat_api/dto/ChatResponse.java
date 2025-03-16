package com.facundo.your_chat_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponse implements Serializable {

    private Long id;

    private String name;

    private LocalDateTime creationDate;

    private LocalDateTime lastMessageDate;
}
