package com.facundo.your_chat_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class RegisterRequest implements Serializable {

    @Email
    private String mail;

    @Size(min = 8)
    private String password;

    private String repeatedPassword;
}
