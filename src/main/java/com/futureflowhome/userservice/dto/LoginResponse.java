package com.futureflowhome.userservice.dto;

public class LoginResponse {

    private final String accessToken;
    private final String tokenType = "Bearer";
    private final long expiresInSeconds;

    public LoginResponse(String accessToken, long expiresInSeconds) {
        this.accessToken = accessToken;
        this.expiresInSeconds = expiresInSeconds;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public long getExpiresInSeconds() {
        return expiresInSeconds;
    }
}
