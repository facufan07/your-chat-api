package com.facundo.your_chat_api.dto;

import java.io.Serializable;

public class RequestLogin implements Serializable {
    private String mail;

    private String password;

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }
}
