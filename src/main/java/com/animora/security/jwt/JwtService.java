package com.animora.security.jwt;

import com.animora.user.entity.User;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@RequiredArgsConstructor
@Service
public class JwtService {

    private final JwtTokenProvider tokenProvider;

    public String generateToken(User user) {
        return tokenProvider.generateToken(
                user.getId(),
                user.getEmail(),
                user.getRoles()
        );
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

    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {
        return tokenProvider.parseClaims(token)
                .get("roles", List.class);
    }
}
