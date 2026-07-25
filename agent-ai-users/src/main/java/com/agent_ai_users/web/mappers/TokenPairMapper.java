package com.agent_ai_users.web.mappers;

import com.agent_ai_users.auth.domain.models.TokenPair;
import com.agent_ai_users.web.models.TokenPairDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TokenPairMapper extends BaseMapper<TokenPair, TokenPairDTO> {
}
