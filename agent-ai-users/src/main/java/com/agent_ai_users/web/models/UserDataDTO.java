package com.agent_ai_users.web.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserDataDTO {
    private Long userDataId;
    private String username;
    private String email;
    private boolean enabled;
    private Set<RoleDTO> roles;
}
