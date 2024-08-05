package com.sparta.chat_test.chat.dto.chatRoom;

import lombok.Getter;

@Getter
public class LastMessageResponseDto {

    private Long userId;
    private String message;

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

