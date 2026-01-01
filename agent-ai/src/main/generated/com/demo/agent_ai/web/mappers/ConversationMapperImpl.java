package com.demo.agent_ai.web.mappers;

import com.demo.agent_ai.chat.domain.models.Conversation;
import com.demo.agent_ai.web.models.ConversationResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-27T12:37:25+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class ConversationMapperImpl implements ConversationMapper {

    @Override
    public Conversation toDocument(ConversationResponse src) {
        if ( src == null ) {
            return null;
        }

        Conversation.ConversationBuilder<?, ?> conversation = Conversation.builder();

        conversation.id( src.getId() );
        conversation.title( src.getTitle() );

        return conversation.build();
    }

    @Override
    public ConversationResponse toModel(Conversation src) {
        if ( src == null ) {
            return null;
        }

        ConversationResponse.ConversationResponseBuilder<?, ?> conversationResponse = ConversationResponse.builder();

        conversationResponse.id( src.getId() );
        conversationResponse.title( src.getTitle() );

        return conversationResponse.build();
    }

    @Override
    public List<Conversation> toDocument(List<ConversationResponse> src) {
        if ( src == null ) {
            return null;
        }

        List<Conversation> list = new ArrayList<Conversation>( src.size() );
        for ( ConversationResponse conversationResponse : src ) {
            list.add( toDocument( conversationResponse ) );
        }

        return list;
    }

    @Override
    public List<ConversationResponse> toModel(List<Conversation> src) {
        if ( src == null ) {
            return null;
        }

        List<ConversationResponse> list = new ArrayList<ConversationResponse>( src.size() );
        for ( Conversation conversation : src ) {
            list.add( toModel( conversation ) );
        }

        return list;
    }
}
