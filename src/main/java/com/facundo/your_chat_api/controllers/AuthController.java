package com.facundo.your_chat_api.controllers;

import com.facundo.your_chat_api.dto.RegisterRequest;
import com.facundo.your_chat_api.dto.RegisterResponse;
import com.facundo.your_chat_api.dto.RequestLogin;
import com.facundo.your_chat_api.services.auth.IAuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@CrossOrigin(
        origins = "http://localhost:3000",
        allowCredentials = "true"
)
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
    public ResponseEntity<Integer> login(@RequestBody RequestLogin requestLogin,
                                                  HttpServletResponse response) {

        RegisterResponse loginResponse = this.authService.login(requestLogin);

        Cookie cookie = new Cookie("auth_token", loginResponse.getToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setDomain("localhost");


        response.addHeader(
                "Set-Cookie", cookie.getName() + "=" + cookie.getValue() +
                "; Path=" + cookie.getPath() +
                "; HttpOnly; Max-Age=" + cookie.getMaxAge() +
                "; SameSite=Lax");


        return ResponseEntity.ok(response.getStatus());
    }

    @GetMapping("/isAuth")
    public ResponseEntity<Boolean> isAuth(HttpServletRequest request) {
        Boolean isAuthenticated = this.authService.isAuthenticated(request);

        return ResponseEntity.ok(isAuthenticated);
    }
}
