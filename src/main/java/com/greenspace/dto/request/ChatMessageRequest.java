package com.greenspace.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class ChatMessageRequest {

    @NotNull(message = "Recipient ID is required")
    private Long recipientId;

    private Long productId; // Optional if message related to a product

    @NotBlank(message = "Message content cannot be empty")
    private String content;
}
