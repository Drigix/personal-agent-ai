package com.demo.agent_ai.web.mappers;

import com.demo.agent_ai.chat.domain.models.ChatMessage;
import com.demo.agent_ai.web.models.ChatMessageResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-27T13:07:44+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class ChatMessageMapperImpl implements ChatMessageMapper {

    @Override
    public ChatMessage toDocument(ChatMessageResponse src) {
        if ( src == null ) {
            return null;
        }

        ChatMessage.ChatMessageBuilder<?, ?> chatMessage = ChatMessage.builder();

        chatMessage.conversationId( src.getConversationId() );
        chatMessage.role( mapStringToRole( src.getRole() ) );
        chatMessage.date( src.getDate() );
        chatMessage.content( src.getContent() );

        return chatMessage.build();
    }

    @Override
    public ChatMessageResponse toModel(ChatMessage src) {
        if ( src == null ) {
            return null;
        }

        ChatMessageResponse.ChatMessageResponseBuilder<?, ?> chatMessageResponse = ChatMessageResponse.builder();

        chatMessageResponse.conversationId( src.getConversationId() );
        chatMessageResponse.role( mapRoleToString( src.getRole() ) );
        chatMessageResponse.date( src.getDate() );
        chatMessageResponse.content( src.getContent() );

        return chatMessageResponse.build();
    }

    @Override
    public List<ChatMessage> toDocument(List<ChatMessageResponse> src) {
        if ( src == null ) {
            return null;
        }

        List<ChatMessage> list = new ArrayList<ChatMessage>( src.size() );
        for ( ChatMessageResponse chatMessageResponse : src ) {
            list.add( toDocument( chatMessageResponse ) );
        }

        return list;
    }

    @Override
    public List<ChatMessageResponse> toModel(List<ChatMessage> src) {
        if ( src == null ) {
            return null;
        }

        List<ChatMessageResponse> list = new ArrayList<ChatMessageResponse>( src.size() );
        for ( ChatMessage chatMessage : src ) {
            list.add( toModel( chatMessage ) );
        }

        return list;
    }
}
