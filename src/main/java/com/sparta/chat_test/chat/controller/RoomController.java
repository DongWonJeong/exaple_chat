package com.sparta.chat_test.chat.controller;

import com.sparta.chat_test.chat.dto.UserRoom.UserRoomRequestDto;
import com.sparta.chat_test.chat.dto.UserRoom.UserRoomResponseDto;
import com.sparta.chat_test.chat.dto.chatRoom.ChatRoomRequestDto;
import com.sparta.chat_test.chat.dto.chatRoom.ChatRoomResponseDto;
import com.sparta.chat_test.chat.dto.user.UserRequestDto;
import com.sparta.chat_test.chat.service.ChatRoomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/foot")
public class RoomController {

    private final ChatRoomService chatRoomService;

    public RoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    // 채팅방 생성
    @PostMapping("/rooms")
    public ChatRoomResponseDto createRoom(@RequestBody ChatRoomRequestDto chatRoomRequestDto) {
        return chatRoomService.createRoom(chatRoomRequestDto);
    }

    // 채팅방 전체 조회
    @GetMapping("/rooms")
    public List<ChatRoomResponseDto> getRoom() {
        return chatRoomService.getRoom();
    }

    // 채팅방 삭제
    @DeleteMapping("/rooms/{roomId}")
    public String deleteRoom(@PathVariable Long roomId) {
        return chatRoomService.deleteRoom(roomId);
    }

    // 채팅방 참여
    @PostMapping("rooms/users")
    public UserRoomResponseDto createUserRoom(@RequestBody UserRoomRequestDto userRoomRequestDto) {
        return chatRoomService.createUserRoom(userRoomRequestDto);
    }

}
