package com.greenspace.service;

import com.greenspace.dto.request.ChatMessageRequest;
import com.greenspace.dto.response.ChatMessageResponse;

import java.util.List;

public interface ChatMessageService {
    ChatMessageResponse saveMessage(ChatMessageRequest request, Long senderId);
    List<ChatMessageResponse> getConversationHistory(Long user1Id, Long user2Id);
    long getUnreadMessagesCount(Long userId);
    void markConversationAsRead(Long senderId, Long recipientId);
}