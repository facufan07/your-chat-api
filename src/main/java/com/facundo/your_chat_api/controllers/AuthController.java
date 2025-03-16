package com.facundo.your_chat_api.controllers;

import com.facundo.your_chat_api.dto.RegisterRequest;
import com.facundo.your_chat_api.dto.RegisterResponse;
import com.facundo.your_chat_api.dto.RequestLogin;
import com.facundo.your_chat_api.services.auth.IAuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisterResponse> register(@RequestBody
                                                       @Valid
                                                         RegisterRequest registerRequest) {

        RegisterResponse registerResponse = this.authService.register(registerRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(registerResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<RegisterResponse> login(@RequestBody RequestLogin requestLogin) {

        RegisterResponse loginResponse = this.authService.login(requestLogin);

        return  ResponseEntity.ok(loginResponse);
    }
}
