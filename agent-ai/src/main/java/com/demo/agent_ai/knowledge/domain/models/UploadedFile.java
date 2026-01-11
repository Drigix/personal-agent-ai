package com.demo.agent_ai.knowledge.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UploadedFile {
    private String filename;
    private byte[] content;
    private String contentType;
    private Long size;

    public BigDecimal roundSizeToMb() {
        return BigDecimal.valueOf(size)
                .divide(BigDecimal.valueOf(1024L * 1024L), 2, RoundingMode.HALF_UP);
    }
}
