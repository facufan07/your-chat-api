package com.facundo.your_chat_api.services.auth;

import com.facundo.your_chat_api.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    @Value("${security.jwt.expiration-in-minutes}")
    private Long EXPIRATION_IN_MINUTES;

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;


    public String generateToken(User user) {

        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date((this.EXPIRATION_IN_MINUTES * 60 * 1000) + issuedAt.getTime());

        String jwt = Jwts.builder()
                .header().type("JWT")
                .and()
                .subject(user.getUsername())
                .issuedAt(issuedAt)
                .expiration(expiration)

                .signWith(this.generateKey(), Jwts.SIG.HS256)
                .compact();

        return jwt;
    }

    private SecretKey generateKey(){
        byte[] passwordDecoded = Decoders.BASE64.decode(this.SECRET_KEY);
        return Keys.hmacShaKeyFor(passwordDecoded);
    }

    private Claims extractAllClaims(String jwt){
        return Jwts.parser().verifyWith(this.generateKey()).build()
                .parseSignedClaims(jwt).getPayload();
    }

    public Date extractExpiration(String jwt) {
        return this.extractAllClaims(jwt).getExpiration();
    }

    public String extractMail(String jwt) {
        return this.extractAllClaims(jwt).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(this.generateKey()).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {

            return false;
        }
    }
}
