package com.agent_ai_users.web.models;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class RoleDTO {
    private Long roleId;
    private String name;
}
