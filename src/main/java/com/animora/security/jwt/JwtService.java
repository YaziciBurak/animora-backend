package com.animora.security.jwt;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;


@RequiredArgsConstructor
@Service
public class JwtService {

    private final JwtTokenProvider tokenProvider;

    public String generateToken(Long userId, String email) {
        return tokenProvider.generateToken(userId, email);
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = tokenProvider.parseClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String extractEmail(String token) {
        return tokenProvider.parseClaims(token).getSubject();
    }

    public Long extractUserId(String token) {
        return tokenProvider.parseClaims(token).get("userId", Long.class);
    }
}
