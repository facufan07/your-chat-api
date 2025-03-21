package com.facundo.your_chat_api.services.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CookieService {
    private final JwtService jwtService;

    public CookieService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public Boolean isAuthenticated(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if("auth_token".equals(cookie.getName())){
                    String token = cookie.getValue();
                    Date now = new Date(System.currentTimeMillis());
                    if(!this.jwtService.validateToken(token)){
                        return false;
                    }

                    if(!this.jwtService.extractExpiration(token).after(now)){
                        return false;
                    }

                    return true;
                }
            }
        }

        return false;
    }
}
