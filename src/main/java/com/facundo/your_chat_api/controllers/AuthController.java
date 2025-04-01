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
        origins = {"http://localhost:3000", "https://your-chat-front-production.up.railway.app/"},
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

        ResponseCookie cookie = ResponseCookie.from("auth_token",
                        registerResponse.getToken())
                .httpOnly(true)
                .secure(environment.equals("production"))
                .path("/")
                .domain(environment.equals("production") ? ".railway.app" : "localhost")
                .maxAge(86400)
                .sameSite("Lax")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(registerResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<Integer> login(@RequestBody RequestLogin requestLogin) {

        RegisterResponse loginResponse = this.authService.login(requestLogin);

        ResponseCookie cookie = ResponseCookie.from("auth_token",
                        loginResponse.getToken())
                .httpOnly(true)
                .secure(environment.equals("production"))
                .path("/")
                .domain(environment.equals("production") ? ".railway.app" : "localhost")
                .maxAge(86400)
                .sameSite("Lax")
                .build();


        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(200);
    }

    @GetMapping("/isAuth")
    public ResponseEntity<Boolean> isAuth(HttpServletRequest request) {
        Boolean isAuthenticated = this.authService.isAuthenticated(request);

        return ResponseEntity.ok(isAuthenticated);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {

        ResponseCookie cookie = ResponseCookie.from("auth_token", "")
                .httpOnly(true)
                .secure(environment.equals("production"))
                .path("/")
                .domain(environment.equals("production") ? ".railway.app" : "localhost")
                .maxAge(86400)
                .sameSite("Lax")
                .build();


        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}
