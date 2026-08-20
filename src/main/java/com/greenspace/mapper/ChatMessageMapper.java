package com.greenspace.mapper;

import com.greenspace.dto.request.ChatMessageRequest;
import com.greenspace.dto.response.ChatMessageResponse;
import com.greenspace.entity.ChatMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChatMessageMapper {

    @Mapping(source = "sender.id", target = "senderId")
    @Mapping(source = "recipient.id", target = "recipientId")
    @Mapping(source = "product.id", target = "productId")
    ChatMessageResponse toResponse(ChatMessage chatMessage);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sender", ignore = true)
    @Mapping(target = "recipient", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "isRead", ignore = true)
    @Mapping(target = "timestamp", ignore = true)
    ChatMessage toEntity(ChatMessageRequest request);
}
