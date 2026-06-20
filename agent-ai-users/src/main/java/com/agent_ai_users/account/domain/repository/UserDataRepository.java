package com.agent_ai_users.account.domain.repository;

import com.agent_ai_users.account.domain.entities.UserData;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

public interface UserDataRepository {
    Optional<UserData> findByUsername(@NonNull String username);
    UserData save(@NonNull UserData userData);
}
