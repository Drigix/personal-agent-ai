package com.demo.agent_ai.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ollama")
@Getter
@Setter
public class OllamaProperties {
    private String model;
    private String host;
    private Integer timeout;
}