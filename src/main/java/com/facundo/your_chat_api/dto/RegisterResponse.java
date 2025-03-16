package com.facundo.your_chat_api.dto;

import java.io.Serializable;

public class RegisterResponse implements Serializable {

    private String token;

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
