package com.agent_ai_users.auth.domain.repository;

import com.agent_ai_users.auth.domain.entities.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken refreshToken);
    Optional<RefreshToken> findByTokenHash(String tokenHash);
    void deleteByUserId(Long userDataId);
    void revokedAllByUserId(Long userDataId);
}
