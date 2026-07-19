package com.agent_ai_users.account.application;

import com.agent_ai_users.account.domain.entities.UserData;
import com.agent_ai_users.account.domain.repository.UserDataRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDataRepository userDataRepository;

    public @Nullable UserData getUserDataByUsername(@NonNull String username) {
        return userDataRepository.findByUsername(username).orElse(null);
    }
}
