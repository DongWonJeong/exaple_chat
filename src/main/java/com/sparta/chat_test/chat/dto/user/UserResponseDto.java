package com.sparta.chat_test.chat.dto.user;

import com.sparta.chat_test.chat.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private Long id;
    private String userName;

    public UserResponseDto(User user) {
        this.id = user.getUserId();
        this.userName = user.getUserName();
    }
}
