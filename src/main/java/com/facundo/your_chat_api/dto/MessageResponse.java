package com.facundo.your_chat_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse implements Serializable {

    private Long id;
    private String text;
    private Boolean type;
    private LocalDateTime creationDate;
}
