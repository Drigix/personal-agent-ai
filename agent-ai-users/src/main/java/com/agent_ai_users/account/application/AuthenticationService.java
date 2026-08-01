package com.agent_ai_users.account.application;

import com.agent_ai_users.account.domain.entities.UserData;
import com.agent_ai_users.account.domain.repository.UserDataRepository;
import com.agent_ai_users.account.infrastructure.JwtUtils;
import com.agent_ai_users.auth.application.RefreshTokenService;
import com.agent_ai_users.auth.domain.models.TokenPair;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final UserDataRepository userDataRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

    public TokenPair authenticate(String username, String password, AuthenticationManager authenticationManager) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        UserData user = (UserData) auth.getPrincipal();
        String accessToken = jwtUtils.generateToken(username);
        String refreshToken = refreshTokenService.createRefreshToken(user);

        return new TokenPair(accessToken, refreshToken);
    }

    public TokenPair refresh(@NonNull String refreshToken) {
        RefreshTokenService.RotatedToken rotated = refreshTokenService.rotate(refreshToken);
        String newAccessToken = jwtUtils.generateToken(rotated.user().getUsername());
        return new TokenPair(newAccessToken, rotated.rawToken());
    }

    public void logout(String refreshToken) {
        refreshTokenService.revokeSingle(refreshToken);
    }

    public boolean isDuplicateEmail(@NonNull String email) {
        return userDataRepository.findByEmail(email).isPresent();
    }

    @Override
    public @Nullable UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        return userDataRepository.findByUsername(username)
                .orElse(null);
    }

    public UserData registerUser(@NonNull UserData user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDataRepository.save(user);
    }
}