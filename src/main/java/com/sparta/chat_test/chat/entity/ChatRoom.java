package com.sparta.chat_test.chat.entity;

import com.sparta.chat_test.chat.dto.chatRoom.ChatRoomRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "chat_rooms")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @Column(name="room_name", nullable = false)
    private String roomName;

    @Column(name="created_date", nullable = false)
    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "chatRoom")
    private List<UserRoom> userRooms = new ArrayList<>();

    public ChatRoom(ChatRoomRequestDto chatRoomRequestDto) {
        this.roomName = chatRoomRequestDto.getRoomName();
        this.createdDate = LocalDateTime.now();
    }
}
