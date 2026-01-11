package com.demo.agent_ai.chat.domain.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FileMetadata {
    private String filename;
    private String contentType;
    private BigDecimal size;
}
