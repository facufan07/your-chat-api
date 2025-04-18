package com.facundo.your_chat_api.services.auth;

import com.facundo.your_chat_api.dto.RegisterRequest;
import com.facundo.your_chat_api.dto.RegisterResponse;

import com.facundo.your_chat_api.dto.RequestLogin;
import com.facundo.your_chat_api.entities.User;
import com.facundo.your_chat_api.services.entitiesService.user.IUserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService implements IAuthService {

    private final JwtService jwtService;

    private final IUserService userService;

    private final CookieService cookieService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthService(JwtService jwtService,
                       IUserService userService,
                       CookieService cookieService) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.cookieService = cookieService;
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {

        User user = this.userService.register(registerRequest);
        String token = this.jwtService.generateToken(user, this.generateExtraClaims(user));

        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setToken(token);

        return registerResponse;
    }

    @Override
    public RegisterResponse login(RequestLogin requestLogin) {

        User user = this.userService.findByMail(requestLogin.getMail());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                requestLogin.getMail(),
                requestLogin.getPassword()
        );

        this.authenticationManager.authenticate(authentication);

        String token = this.jwtService.generateToken(user, this.generateExtraClaims(user));

        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setToken(token);

        return registerResponse;
    }

    @Override
    public User getUser() {
        String mail = SecurityContextHolder.getContext().getAuthentication().getName();
        return this.userService.findByMail(mail);
    }

    @Override
    public Boolean isAuthenticated(HttpServletRequest request) {
        return this.cookieService.isAuthenticated(request);
    }

    private Map<String, Object> generateExtraClaims(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("mail", user.getUsername());
        extraClaims.put("role", user.getRole());
        extraClaims.put("authorities", user.getAuthorities());

        return extraClaims;
    }


}
