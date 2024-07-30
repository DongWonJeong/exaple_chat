package com.sparta.chat_test.chat.dto.chatMessage;

import com.sparta.chat_test.chat.entity.ChatMessage;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatMessageResponseDto {

    private Long messageId;
    private Long userId;
    private String message;
    private LocalDateTime sendDate;

    public ChatMessageResponseDto(ChatMessage chatMessage) {
        this.messageId = chatMessage.getMessageId();
        this.userId = chatMessage.getUser().getId();
        this.message = chatMessage.getMessage();
        this.sendDate = LocalDateTime.now();
    }
}
