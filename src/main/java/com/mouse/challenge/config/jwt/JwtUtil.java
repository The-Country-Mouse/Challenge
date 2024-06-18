package com.mouse.challenge.config.jwt;

public interface JwtUtil {
    String SECRET = "auth";
    int EXPIRATION_TIME = 1000 * 60 * 60; // 1시간
    String TOKEN_PREFIX = "Bearer";
    String HEADER_STRING = "Authorization";
}
