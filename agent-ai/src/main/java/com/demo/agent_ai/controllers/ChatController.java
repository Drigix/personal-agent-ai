package com.demo.agent_ai.controllers;

import com.demo.agent_ai.services.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chatSerivce")
@SessionAttributes("memoryChatService")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/getChatResponse/{message}")
    public ResponseEntity<String> getChatResponse(@PathVariable("message") String message) {
        String result = chatService.chat(message);
        return ResponseEntity.ok(result);
    }

}
