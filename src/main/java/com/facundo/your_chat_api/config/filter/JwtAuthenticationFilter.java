package com.facundo.your_chat_api.config.filter;

import com.facundo.your_chat_api.entities.User;
import com.facundo.your_chat_api.exception.InvalidTokenException;
import com.facundo.your_chat_api.services.auth.JwtService;
import com.facundo.your_chat_api.services.entitiesService.user.IUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final IUserService userService;

    public JwtAuthenticationFilter(JwtService jwtService, IUserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();
        if (path.startsWith("/auth/") && (path.endsWith("/login") || path.endsWith("/register"))) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = this.getToken(request);
        this.isExpired(token);

        String mail = this.jwtService.extractMail(token);
        User user = this.userService.findByMail(mail);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            mail, null, user.getAuthorities()
        );

        authToken.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String tokenWithBearer = request.getHeader("Authorization");

        if(tokenWithBearer == null || !tokenWithBearer.startsWith("Bearer ") || !StringUtils.hasText(tokenWithBearer)){
            throw new InvalidTokenException("Token not found");
        }

        return tokenWithBearer.substring(7);
    }

    private void isExpired(String token) {
        Date now = new Date(System.currentTimeMillis());

        if(!this.jwtService.extractExpiration(token).after(now)){
            throw new InvalidTokenException("Token expired");
        }
    }
}
