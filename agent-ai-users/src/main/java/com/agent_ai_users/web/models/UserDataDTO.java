package com.agent_ai_users.web.models;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@SuperBuilder
public class UserDataDTO {
    private String username;
    private boolean enabled;
    private Set<RoleDTO> roles;
}
