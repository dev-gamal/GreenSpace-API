package com.greenspace.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class ConversationResponse {
    private Long userId;
    private String firstName;
    private String lastName;
    private String lastMessage;
    private LocalDateTime lastMessageTime;
    private long unreadCount;
}
