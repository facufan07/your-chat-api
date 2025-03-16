package com.facundo.your_chat_api.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateChatRequest implements Serializable {

    private Long id;

    private String newName;

}
