package com.demo.agent_ai.web.controllers;

import com.demo.agent_ai.chat.application.ChatService;
import com.demo.agent_ai.web.mappers.ChatMessageMapper;
import com.demo.agent_ai.web.mappers.ConversationMapper;
import com.demo.agent_ai.web.mappers.UploadedFileMapper;
import com.demo.agent_ai.web.models.ChatMessageResponse;
import com.demo.agent_ai.web.models.ChatRequestBody;
import com.demo.agent_ai.web.models.ConversationRequestBody;
import com.demo.agent_ai.web.models.ConversationResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/chatService")
@SessionAttributes("memoryChatService")
@AllArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final ConversationMapper conversationMapper;
    private final ChatMessageMapper chatMessageMapper;
    private final UploadedFileMapper uploadedFileMapper;

    @PostMapping(value = "/generateChatRequest", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ChatMessageResponse> generateChatRequest(
            @RequestPart("request") ChatRequestBody body,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) throws IOException {
        ChatMessageResponse result = chatMessageMapper.toModel(
                chatService.chat(body, uploadedFileMapper.toModel(files))
        );
        return ResponseEntity.ok(result);
    }

    @PostMapping("/getConversations")
    public ResponseEntity<List<ConversationResponse>> getConversations(@RequestBody ConversationRequestBody requestBody) {
        List<ConversationResponse> results = conversationMapper.toModel(chatService.getConversations(requestBody.getUserDataId()));
        return ResponseEntity.ok(results);
    }

    @PostMapping("/getChatHistoryByConversationId")
    public ResponseEntity<List<ChatMessageResponse>> getChatHistoryByConversationId(
            @RequestBody ConversationRequestBody requestBody
    ) {
        List<ChatMessageResponse> results = chatMessageMapper.toModel(chatService.getChatHistoryByConversationId(requestBody.getConversationId()));
        return ResponseEntity.ok(results);
    }

    @DeleteMapping("/deleteConversation/{conversationId}")
    public ResponseEntity<String> deleteConversation(
            @PathVariable("conversationId") String conversationId
    ) {
        chatService.deleteConversation(conversationId);
        return ResponseEntity.noContent().build();
    }
}
