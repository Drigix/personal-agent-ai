package com.agent_ai_users.auth.application.port.in;

import com.agent_ai_users.account.domain.entities.UserData;
import com.agent_ai_users.auth.domain.models.TokenPair;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthenticationIngestPort extends UserDetailsService {
    TokenPair authenticate(String username, String password, AuthenticationManager authenticationManager);
    TokenPair refresh(String refreshToken);
    void logout(String refreshToken);
    UserDetails loadUserByUsername(String username);
    UserData registerUser(UserData user);
}
