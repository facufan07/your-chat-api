package com.facundo.your_chat_api.services.entitiesService.user;

import com.facundo.your_chat_api.dto.RegisterRequest;
import com.facundo.your_chat_api.entities.User;

public interface IUserService {
    User register(RegisterRequest registerRequest);

    User findByMail(String mail);
}
