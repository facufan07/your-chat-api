package com.facundo.your_chat_api.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CreateMessageRequest implements Serializable {

    private Long chatId;

    private String message;

    private Boolean type;
}
