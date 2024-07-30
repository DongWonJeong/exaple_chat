package com.sparta.chat_test.chat.repository;

import com.sparta.chat_test.chat.entity.UserRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoomRepository extends JpaRepository<UserRoom, Long> {
}
