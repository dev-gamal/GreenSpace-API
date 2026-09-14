package com.greenspace.controller;

import com.greenspace.dto.response.ChatMessageResponse;
import com.greenspace.dto.response.ConversationResponse;
import com.greenspace.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @GetMapping("/history")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ChatMessageResponse>> getConversationHistory(
            @RequestParam Long user1Id,
            @RequestParam Long user2Id) {
        return ResponseEntity.ok(chatMessageService.getConversationHistory(user1Id, user2Id));
    }

    @GetMapping("/unread-count")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Long> getUnreadMessagesCount(@RequestParam Long userId) {
        return ResponseEntity.ok(chatMessageService.getUnreadMessagesCount(userId));
    }

    @PutMapping("/mark-read")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> markConversationAsRead(
            @RequestParam Long senderId,
            @RequestParam Long recipientId) {
        chatMessageService.markConversationAsRead(senderId, recipientId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/conversations")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ConversationResponse>> getConversations(@RequestParam Long userId) {
        return ResponseEntity.ok(chatMessageService.getConversations(userId));
    }
}
