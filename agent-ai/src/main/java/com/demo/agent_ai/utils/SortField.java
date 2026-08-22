package com.demo.agent_ai.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Sort;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SortField {
    @Builder.Default
    private String field = "id";
    @Builder.Default
    private Sort.Direction  direction = Sort.Direction.ASC;
}
