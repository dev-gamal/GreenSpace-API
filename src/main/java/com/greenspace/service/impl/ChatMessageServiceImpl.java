package com.greenspace.service.impl;

import com.greenspace.dto.request.ChatMessageRequest;
import com.greenspace.dto.response.ChatMessageResponse;
import com.greenspace.entity.ChatMessage;
import com.greenspace.entity.Product;
import com.greenspace.entity.User;
import com.greenspace.mapper.ChatMessageMapper;
import com.greenspace.repository.ChatMessageRepository;
import com.greenspace.repository.ProductRepository;
import com.greenspace.repository.UserRepository;
import com.greenspace.service.ChatMessageService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ChatMessageMapper chatMessageMapper;

    @Override
    public ChatMessageResponse saveMessage(ChatMessageRequest request, Long senderId) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new EntityNotFoundException("Sender not found"));
        User recipient = userRepository.findById(request.getRecipientId())
                .orElseThrow(() -> new EntityNotFoundException("Recipient not found"));

        Product product = null;
        if (request.getProductId() != null) {
            product = productRepository.findById(request.getProductId()).orElse(null);
        }

        ChatMessage message = chatMessageMapper.toEntity(request);
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setProduct(product);
        message.setIsRead(false);

        return chatMessageMapper.toResponse(chatMessageRepository.save(message));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessageResponse> getConversationHistory(Long user1Id, Long user2Id) {
        return chatMessageRepository.findConversationHistory(user1Id, user2Id).stream()
                .map(chatMessageMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public long getUnreadMessagesCount(Long userId) {
        return chatMessageRepository.countUnreadMessages(userId);
    }

    @Override
    public void markConversationAsRead(Long senderId, Long recipientId) {
        List<ChatMessage> unreadMessages = chatMessageRepository.findConversationHistory(senderId, recipientId).stream()
                .filter(msg -> msg.getRecipient().getId().equals(recipientId) && !msg.getIsRead())
                .toList();

        unreadMessages.forEach(msg -> msg.setIsRead(true));
        chatMessageRepository.saveAll(unreadMessages);
    }
}