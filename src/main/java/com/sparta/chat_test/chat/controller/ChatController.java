package com.sparta.chat_test.chat.controller;

import com.sparta.chat_test.chat.dto.userRoom.UserRoomRequestDto;
import com.sparta.chat_test.chat.dto.userRoom.UserRoomResponseDto;
import com.sparta.chat_test.chat.dto.chatMessage.ChatMessageRequestDto;
import com.sparta.chat_test.chat.dto.chatMessage.ChatMessageResponseDto;
import com.sparta.chat_test.chat.dto.chatMessage.ReadMessageResponseDto;
import com.sparta.chat_test.chat.dto.chatRoom.ChatRoomRequestDto;
import com.sparta.chat_test.chat.dto.chatRoom.ChatRoomResponseDto;
import com.sparta.chat_test.chat.dto.chatRoom.RoomListResponseDto;
import com.sparta.chat_test.chat.service.ChatMessageService;
import com.sparta.chat_test.chat.service.ChatRoomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/foot/chat/rooms")
public class  ChatController {

    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;

    public ChatController(ChatRoomService chatRoomService, ChatMessageService chatMessageService) {
        this.chatRoomService = chatRoomService;
        this.chatMessageService = chatMessageService;
    }

    // 채팅방 생성
    @PostMapping()
    public ChatRoomResponseDto createRoom(@RequestBody ChatRoomRequestDto chatRoomRequestDto) {
        return chatRoomService.createRoom(chatRoomRequestDto);
    }

    // 채팅방 전체 조회
    @GetMapping()
    public List<RoomListResponseDto> getListRoom() {
        return chatRoomService.getListRoom();
    }

    // 채팅방 삭제
    @DeleteMapping("/{roomId}")
    public String deleteRoom(@PathVariable Long roomId, @RequestParam Long userId) {
        return chatRoomService.deleteRoom(roomId, userId);
    }

    // 채팅방 참여
    @PostMapping("/join")
    public UserRoomResponseDto createUserRoom(@RequestBody UserRoomRequestDto userRoomRequestDto) {
        return chatRoomService.createUserRoom(userRoomRequestDto);
    }

    // 채팅 전송
    @PostMapping("/{roomId}/messages")
    public ChatMessageResponseDto createChatMessage(@PathVariable Long roomId,
                                                    @RequestBody ChatMessageRequestDto chatMessageRequestDto) {

        return chatMessageService.createChatMessage(roomId, chatMessageRequestDto);
    }

    // 채팅 조회
    @GetMapping("/{roomId}/messages")
    public List<ReadMessageResponseDto> getChatMessagesList(@PathVariable Long roomId) {
        return chatMessageService.getChatMessageList(roomId);
    }
}
