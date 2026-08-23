package com.agent_ai_users.web.models;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@SuperBuilder
public class UserDataDTO {
    private Long userDataId;
    private String username;
    private String email;
    private boolean enabled;
    private Set<RoleDTO> roles;
}
