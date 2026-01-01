package com.demo.agent_ai.config;

import com.demo.agent_ai.chat.domain.converters.ChatMessageRoleConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.List;

@Configuration
public class MongoConfig {

    @Bean
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(List.of(
                new ChatMessageRoleConverter.StringToChatMessageRoleConverter(),
                new ChatMessageRoleConverter.ChatMessageRoleToStringConverter()
        ));
    }
}
