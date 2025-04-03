package com.facundo.your_chat_api.controllers;

import com.facundo.your_chat_api.dto.RegisterRequest;
import com.facundo.your_chat_api.dto.RegisterResponse;
import com.facundo.your_chat_api.dto.RequestLogin;
import com.facundo.your_chat_api.services.auth.IAuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@CrossOrigin(
        origins = {"http://localhost:3000", "https://your-chat-front-production.up.railway.app/", "https://your-chat-hazel.vercel.app/"},
        allowCredentials = "true"
)
public class AuthController {

    private final IAuthService authService;

    @Value("${environment}")
    private String environment;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisterResponse> register(@RequestBody
                                                       @Valid
                                                         RegisterRequest registerRequest,
                                                      HttpServletResponse response) {

        RegisterResponse registerResponse = this.authService.register(registerRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(registerResponse);

    }

    @PostMapping("/login")
    public ResponseEntity<RegisterResponse> login(@RequestBody RequestLogin requestLogin) {

        RegisterResponse loginResponse = this.authService.login(requestLogin);

        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/isAuth")
    public ResponseEntity<Boolean> isAuth() {
        return ResponseEntity.ok(true);
    }

}
