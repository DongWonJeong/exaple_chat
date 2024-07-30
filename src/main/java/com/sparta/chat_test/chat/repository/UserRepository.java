package com.sparta.chat_test.chat.repository;

import com.sparta.chat_test.chat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
