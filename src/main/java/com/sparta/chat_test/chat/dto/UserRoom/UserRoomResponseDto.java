package com.sparta.chat_test.chat.dto.UserRoom;

import com.sparta.chat_test.chat.entity.UserRoom;
import lombok.Getter;

@Getter
public class UserRoomResponseDto {

    private Long id;
    private Long roomId;
    private Long userId;

    public UserRoomResponseDto(UserRoom userRoom) {
        this.id = userRoom.getId();
        this.roomId = userRoom.getChatRoom().getRoomId();
        this.userId = userRoom.getUser().getUserId();
    }
}
