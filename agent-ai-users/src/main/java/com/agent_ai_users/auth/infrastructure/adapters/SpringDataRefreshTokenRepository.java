package com.agent_ai_users.auth.infrastructure.adapters;

import com.agent_ai_users.auth.domain.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SpringDataRefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByTokenHash(String tokenHash);

    void deleteByUser_UserDataId(Long userDataId);

    @Modifying
    @Query("UPDATE RefreshToken r SET r.revoked = true WHERE r.user.userDataId = :userId AND r.revoked = false")
    void revokeAllByUserId(@Param("userId") Long userId);
}
