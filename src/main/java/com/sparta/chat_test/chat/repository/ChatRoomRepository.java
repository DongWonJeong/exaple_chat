package com.sparta.chat_test.chat.repository;

import com.sparta.chat_test.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findAllByOrderByCreatedDateAsc();

    boolean existsByRoomName(String roomName);
}
