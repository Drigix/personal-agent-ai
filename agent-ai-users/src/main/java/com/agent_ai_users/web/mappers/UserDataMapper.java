package com.agent_ai_users.web.mappers;

import com.agent_ai_users.account.domain.entities.UserData;
import com.agent_ai_users.web.models.UserDataDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDataMapper extends BaseMapper<UserData, UserDataDTO>{
}
