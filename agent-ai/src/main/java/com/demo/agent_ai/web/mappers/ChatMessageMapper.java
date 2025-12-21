package com.demo.agent_ai.web.mappers;

import com.demo.agent_ai.shared.domain.documents.ChatMessage;
import com.demo.agent_ai.web.models.ChatMessageResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatMessageMapper extends BaseMapper<ChatMessage, ChatMessageResponse>{
}
