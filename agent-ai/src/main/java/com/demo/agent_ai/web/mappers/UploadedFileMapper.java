package com.demo.agent_ai.web.mappers;

import com.demo.agent_ai.knowledge.domain.models.UploadedFile;
import org.mapstruct.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UploadedFileMapper {

    default List<UploadedFile> toModel(List<MultipartFile> multipartFiles) throws IOException {
        if (multipartFiles == null || multipartFiles.isEmpty()) {
            return null;
        }
        return multipartFiles.stream().map(file -> {
            try {
                return toModel(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }

    default UploadedFile toModel(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }

        return UploadedFile.builder()
                .filename(multipartFile.getOriginalFilename())
                .content(multipartFile.getBytes())
                .contentType(multipartFile.getContentType())
                .size(multipartFile.getSize())
                .build();
    }
}
