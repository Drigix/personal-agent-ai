package com.agent_ai_users.auth.application;

import com.agent_ai_users.account.application.port.in.UserIngestPort;
import com.agent_ai_users.account.domain.entities.UserData;
import com.agent_ai_users.auth.application.port.in.AuthenticationIngestPort;
import com.agent_ai_users.auth.infrastructure.jwt.JwtUtils;
import com.agent_ai_users.auth.domain.models.TokenPair;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationIngestPort {

    private final UserIngestPort userIngestPort;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

    public TokenPair authenticate(String username, String password, AuthenticationManager authenticationManager) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            UserData user = (UserData) auth.getPrincipal();
            String accessToken = jwtUtils.generateToken(user);
            String refreshToken = refreshTokenService.createRefreshToken(user);

            return new TokenPair(accessToken, refreshToken);
        } catch (Exception e) {
            throw new BadCredentialsException("error.userBadCredentials");
        }
    }

    public TokenPair refresh(@NonNull String refreshToken) {
        RefreshTokenService.RotatedToken rotated = refreshTokenService.rotate(refreshToken);
        String newAccessToken = jwtUtils.generateToken(rotated.user());
        return new TokenPair(newAccessToken, rotated.rawToken());
    }

    public void logout(String refreshToken) {
        refreshTokenService.revokeSingle(refreshToken);
    }

    public boolean isDuplicateEmail(@NonNull String email) {
        return userIngestPort.findByEmail(email).isPresent();
    }

    @Override
    public @Nullable UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        return userIngestPort.findByUsername(username)
                .orElse(null);
    }

    public UserData registerUser(@NonNull UserData user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userIngestPort.save(user);
    }
}