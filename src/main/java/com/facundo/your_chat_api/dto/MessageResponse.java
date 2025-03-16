package com.facundo.your_chat_api.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class MessageResponse implements Serializable {

    private Long id;
    private String message;
    private String type;
    private LocalDateTime creationDate;
}
