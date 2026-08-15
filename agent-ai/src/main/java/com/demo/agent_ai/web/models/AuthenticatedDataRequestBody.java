package com.demo.agent_ai.web.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticatedDataRequestBody {
    @NonNull
    private Long userDataId;
    private String username;
    private List<String> roles;
}
