package com.sparta.chat_test.chat.dto.chatRoom;

import com.sparta.chat_test.chat.entity.ChatRoom;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RoomListResponseDto {

    private Long roomId;
    private String roomName;
    private LastMessageResponseDto lastMessage;
    private LocalDateTime createdDate;

    public RoomListResponseDto(ChatRoom chatRoom, LastMessageResponseDto lastMessage) {
        this.roomId = chatRoom.getRoomId();
        this.roomName = chatRoom.getRoomName();
        this.lastMessage = lastMessage;
        this.createdDate = LocalDateTime.now();
    }
}

