package com.sparta.chat_test.chat.repository;

import com.sparta.chat_test.chat.entity.ChatMessage;
import com.sparta.chat_test.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatRoom(ChatRoom chatRoom);
}
