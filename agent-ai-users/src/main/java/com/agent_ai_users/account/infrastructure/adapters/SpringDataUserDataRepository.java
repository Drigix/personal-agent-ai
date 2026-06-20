package com.agent_ai_users.account.infrastructure.adapters;

import com.agent_ai_users.account.domain.entities.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataUserDataRepository extends JpaRepository<UserData, Long> {

    Optional<UserData> findUserDataByUsername(String username);
}
