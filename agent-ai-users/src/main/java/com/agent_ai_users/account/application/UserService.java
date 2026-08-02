package com.agent_ai_users.account.application;

import com.agent_ai_users.account.application.port.in.UserIngestPort;
import com.agent_ai_users.account.domain.entities.UserData;
import com.agent_ai_users.account.domain.repository.UserDataRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserIngestPort {

    private final UserDataRepository userDataRepository;

    @Override
    public Optional<UserData> findByEmail(@NonNull String email) {
        return userDataRepository.findByEmail(email);
    }

    @Override
    public Optional<UserData> findByUsername(@NonNull String username) {
        return userDataRepository.findByUsername(username);
    }

    @Override
    public UserData save(@NonNull UserData user) {
        return userDataRepository.save(user);
    }
}
