package com.demo.agent_ai.web.mappers;

import com.demo.agent_ai.shared.domain.documents.Conversation;
import com.demo.agent_ai.web.models.ConversationResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConversationMapper extends BaseMapper<Conversation, ConversationResponse>{
}
