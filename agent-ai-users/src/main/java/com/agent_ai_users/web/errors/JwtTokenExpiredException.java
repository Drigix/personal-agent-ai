package com.agent_ai_users.web.errors;

import org.springframework.security.authentication.BadCredentialsException;

public class JwtTokenExpiredException extends BadCredentialsException {
    public JwtTokenExpiredException(String message) {
        super(message);
    }
}
