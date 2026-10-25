package com.agent_ai_users.account.application.port.in;

import com.agent_ai_users.account.domain.entities.UserData;
import org.apache.coyote.BadRequestException;

import java.util.Optional;

public interface UserIngestPort {
    Optional<UserData> findById(Long userDataId);
    Optional<UserData> findByEmail(String email);
    Optional<UserData> findByUsername(String username);
    UserData save(UserData user);
    UserData update(UserData user) throws BadRequestException;
}
