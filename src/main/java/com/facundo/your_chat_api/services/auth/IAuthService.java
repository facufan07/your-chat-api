package com.facundo.your_chat_api.services.auth;

import com.facundo.your_chat_api.dto.RegisterRequest;
import com.facundo.your_chat_api.dto.RegisterResponse;
import com.facundo.your_chat_api.dto.RequestLogin;
import com.facundo.your_chat_api.entities.User;

public interface IAuthService {
    RegisterResponse register(RegisterRequest registerRequest);

    RegisterResponse login(RequestLogin requestLogin);

    User getUser();
}
