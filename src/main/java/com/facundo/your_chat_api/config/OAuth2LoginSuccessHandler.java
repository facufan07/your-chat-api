package com.facundo.your_chat_api.config;

import com.facundo.your_chat_api.entities.User;
import com.facundo.your_chat_api.exception.ObjectNotFoundException;
import com.facundo.your_chat_api.services.auth.IAuthService;
import com.facundo.your_chat_api.services.auth.JwtService;
import com.facundo.your_chat_api.services.entitiesService.user.IUserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Value("${frontend.url}")
    private String urlFrontend;

    private final IUserService userService;

    private final IAuthService authService;

    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;

        if(token.getAuthorizedClientRegistrationId().equals("github") || token.getAuthorizedClientRegistrationId().equals("google")) {
            DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();

            Map<String, Object> attributes = user.getAttributes();
            String email = attributes.getOrDefault("email", "").toString();

            User userDB;

            try{
                userDB = this.userService.findByMail(email);
            }
            catch (ObjectNotFoundException e){
                userDB = this.authService.registerOAuth2(email);
            }

            String jwt = this.jwtService.generateToken(userDB);

            String redirectUrl = this.urlFrontend + "/success#token=" + jwt;
            this.getRedirectStrategy().sendRedirect(request, response, redirectUrl);
        }
    }
}
