package com.agent_ai_users.auth.infrastructure.repository;

import com.agent_ai_users.auth.domain.entities.RefreshToken;
import com.agent_ai_users.auth.domain.repository.RefreshTokenRepository;
import com.agent_ai_users.auth.infrastructure.adapters.SpringDataRefreshTokenRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final SpringDataRefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken save(@NonNull RefreshToken refreshToken) {
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public Optional<RefreshToken> findByTokenHash(@NonNull String tokenHash) {
        return  refreshTokenRepository.findByTokenHash(tokenHash);
    }

    @Override
    public void deleteByUserId(@NonNull Long userDataId) {
        refreshTokenRepository.deleteByUser_UserDataId(userDataId);
    }

    @Override
    public void revokedAllByUserId(@NonNull Long userDataId) {
        refreshTokenRepository.revokeAllByUserId(userDataId);
    }
}
