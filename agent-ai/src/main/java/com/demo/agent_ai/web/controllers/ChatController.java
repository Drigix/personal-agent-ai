package com.demo.agent_ai.web.controllers;

import com.demo.agent_ai.web.mappers.ChatMessageMapper;
import com.demo.agent_ai.web.mappers.ConversationMapper;
import com.demo.agent_ai.web.models.ChatMessageResponse;
import com.demo.agent_ai.web.models.ChatRequestBody;
import com.demo.agent_ai.shared.application.ChatService;
import com.demo.agent_ai.web.models.ConversationResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chatSerivce")
@SessionAttributes("memoryChatService")
@AllArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final ConversationMapper conversationMapper;
    private final ChatMessageMapper chatMessageMapper;

    @PostMapping("/generateChatRequest")
    public ResponseEntity<String> generateChatRequest(@RequestBody ChatRequestBody body) {
        String result = chatService.chat(body);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getConversations")
    public ResponseEntity<List<ConversationResponse>> getConversations() {
        List<ConversationResponse> results = conversationMapper.toModel(chatService.getConversations());
        return ResponseEntity.ok(results);
    }

    @GetMapping("/getChatHistoryByConversationId/{conversationId}")
    public ResponseEntity<List<ChatMessageResponse>> getChatHistoryByConversationId(
             @PathVariable("conversationId") String conversationId
    ) {
        List<ChatMessageResponse> results = chatMessageMapper.toModel(chatService.getChatHistoryByConversationId(conversationId));
        return ResponseEntity.ok(results);
    }
}
