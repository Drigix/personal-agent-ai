package com.agent_ai_users.account.domain.repository;

import com.agent_ai_users.account.domain.entities.UserData;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

public interface UserDataRepository {
    Optional<UserData> findByUsername(@NonNull String username);
    Optional<UserData> findByEmail(@NonNull String email);
    UserData save(@NonNull UserData userData);
}
