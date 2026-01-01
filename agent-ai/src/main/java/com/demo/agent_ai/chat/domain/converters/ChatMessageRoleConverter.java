package com.demo.agent_ai.chat.domain.converters;


import com.demo.agent_ai.chat.domain.enums.ChatMessageRole;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

public class ChatMessageRoleConverter{

    @ReadingConverter
    public static class StringToChatMessageRoleConverter implements Converter<String, ChatMessageRole> {
        @Override
        public ChatMessageRole convert(String source) {
            return ChatMessageRole.fromCode(source);
        }
    }

    @WritingConverter
    public static class ChatMessageRoleToStringConverter implements Converter<ChatMessageRole, String> {
        @Override
        public String convert(ChatMessageRole source) {
            return source.getChatMessageRole();
        }
    }
}
