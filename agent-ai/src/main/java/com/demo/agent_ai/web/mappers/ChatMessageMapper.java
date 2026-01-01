package com.demo.agent_ai.web.mappers;

import com.demo.agent_ai.chat.domain.enums.ChatMessageRole;
import com.demo.agent_ai.chat.domain.models.ChatMessage;
import com.demo.agent_ai.web.models.ChatMessageResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatMessageMapper extends BaseMapper<ChatMessage, ChatMessageResponse>{

    default String mapRoleToString(ChatMessageRole role) {
        return role != null ? role.getChatMessageRole() : null;
    }

    default ChatMessageRole mapStringToRole(String code) {
        return code != null ?  ChatMessageRole.fromCode(code) : null;
    }
}
