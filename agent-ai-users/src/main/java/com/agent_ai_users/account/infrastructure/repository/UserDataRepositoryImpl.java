package com.agent_ai_users.account.infrastructure.repository;

import com.agent_ai_users.account.domain.entities.UserData;
import com.agent_ai_users.account.domain.repository.UserDataRepository;
import com.agent_ai_users.account.infrastructure.adapters.SpringDataUserDataRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDataRepositoryImpl implements UserDataRepository {

    private final SpringDataUserDataRepository springDataUserDataRepository;

    @Override
    public Optional<UserData> findByUsername(@NonNull String username) {
        return springDataUserDataRepository.findUserDataByUsername(username);
    }

    @Override
    public UserData save(@NonNull UserData userData) {
        return springDataUserDataRepository.save(userData);
    }
}
