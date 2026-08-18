package com.greenspace.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class ChatMessageResponse {
    private Long id;
    private Long senderId;
    private Long recipientId;
    private Long productId;
    private String content;
    private Boolean isRead;
    private LocalDateTime timestamp;
}