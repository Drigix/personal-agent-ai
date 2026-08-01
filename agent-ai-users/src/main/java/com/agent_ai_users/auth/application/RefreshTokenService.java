package com.agent_ai_users.auth.application;

import com.agent_ai_users.account.domain.entities.UserData;
import com.agent_ai_users.auth.domain.entities.RefreshToken;
import com.agent_ai_users.auth.domain.repository.RefreshTokenRepository;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final Long refreshExpirationMs;

    private final SecureRandom secureRandom = new SecureRandom();

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository,
    @Value("${jwt.refresh-expiration-ms:604800000}") Long refreshExpirationMs) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.refreshExpirationMs = refreshExpirationMs;
    }

    /**
     * Creates a new refresh token for the user, returning the explicit version (to be passed to the client).
     * Only the hash is stored in the database.
     */
    @Transactional
    public String createRefreshToken(UserData user) {
        String rawToken = generateSecureRandomToken();

        RefreshToken entity = RefreshToken.builder()
                .tokenHash(hash(rawToken))
                .user(user)
                .expiryDate(Instant.now().plusMillis(refreshExpirationMs))
                .build();

        refreshTokenRepository.save(entity);
        return rawToken;
    }

    /**
     * Verifies the refresh token, rotates it (invalidates the old one, creates a new one)
     * and returns a new public token + user.
     * If an attempt to use an already revoked token is detected, we treat this as
     * a potential leak and invalidate ALL user tokens.
     */
    @Transactional
    public RotatedToken rotate(String rawToken) {
        String incomingHash = hash(rawToken);

        RefreshToken existing = refreshTokenRepository.findByTokenHash(incomingHash)
                .orElseThrow(() -> new InvalidParameterException("error.unknownRefreshToken"));

        if (existing.isRevoked()) {
            // Reuse detection
            refreshTokenRepository.revokedAllByUserId(existing.getUser().getUserDataId());
            throw new InvalidParameterException(
                    "error.invalidTokenDetected");
        }

        if (existing.getExpiryDate().isBefore(Instant.now())) {
            throw new InvalidParameterException("error.refreshTokenHasExpired");
        }

        // Revoke old token
        existing.setRevoked(true);

        String newRawToken = generateSecureRandomToken();
        String newHash = hash(newRawToken);
        existing.setReplacedByTokenHash(newHash);
        refreshTokenRepository.save(existing);

        RefreshToken newEntity = RefreshToken.builder()
                .tokenHash(newHash)
                .user(existing.getUser())
                .expiryDate(Instant.now().plusMillis(refreshExpirationMs))
                .build();
        refreshTokenRepository.save(newEntity);

        return new RotatedToken(newRawToken, existing.getUser());
    }

    @Transactional
    public void revokeAllForUser(Long userId) {
        refreshTokenRepository.revokedAllByUserId(userId);
    }

    @Transactional
    public void revokeSingle(String rawToken) {
        refreshTokenRepository.findByTokenHash(hash(rawToken))
                .ifPresent(t -> {
                    t.setRevoked(true);
                    refreshTokenRepository.save(t);
                });
    }

    private String generateSecureRandomToken() {
        byte[] bytes = new byte[64]; // 512 bits
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String hash(String rawToken) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashed = digest.digest(rawToken.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashed);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    public record RotatedToken(String rawToken, UserData user) {}
}