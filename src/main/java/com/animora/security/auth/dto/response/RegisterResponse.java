package com.animora.security.auth.dto.response;


public record RegisterResponse(
        String accessToken,
        String tokenType
) {
    public RegisterResponse(String accessToken) {
        this(accessToken, "Bearer");
    }
}
