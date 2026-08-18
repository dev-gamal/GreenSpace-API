package com.greenspace.repository;

import com.greenspace.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @Query("SELECT c FROM ChatMessage c WHERE " +
            "(c.sender.id = :user1Id AND c.recipient.id = :user2Id) OR " +
            "(c.sender.id = :user2Id AND c.recipient.id = :user1Id) " +
            "ORDER BY c.timestamp ASC")
    List<ChatMessage> findConversationHistory(@Param("user1Id") Long user1Id, @Param("user2Id") Long user2Id);

    @Query("SELECT COUNT(c) FROM ChatMessage c WHERE c.recipient.id = :userId AND c.isRead = false")
    long countUnreadMessages(@Param("userId") Long userId);
}
