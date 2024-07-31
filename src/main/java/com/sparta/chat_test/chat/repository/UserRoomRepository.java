package com.sparta.chat_test.chat.repository;

import com.sparta.chat_test.chat.entity.ChatRoom;
import com.sparta.chat_test.chat.entity.UserRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoomRepository extends JpaRepository<UserRoom, Long> {

    void deleteByChatRoom(ChatRoom chatRoom);
}
