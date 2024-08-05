package com.sparta.chat_test.chat.service;

import com.sparta.chat_test.chat.dto.UserRoom.UserRoomRequestDto;
import com.sparta.chat_test.chat.dto.UserRoom.UserRoomResponseDto;
import com.sparta.chat_test.chat.dto.chatRoom.ChatRoomRequestDto;
import com.sparta.chat_test.chat.dto.chatRoom.ChatRoomResponseDto;
import com.sparta.chat_test.chat.dto.chatRoom.LastMessageResponseDto;
import com.sparta.chat_test.chat.dto.chatRoom.RoomListResponseDto;
import com.sparta.chat_test.chat.entity.ChatMessage;
import com.sparta.chat_test.chat.entity.ChatRoom;
import com.sparta.chat_test.chat.entity.User;
import com.sparta.chat_test.chat.entity.UserRoom;
import com.sparta.chat_test.chat.global.CustomException;
import static com.sparta.chat_test.chat.global.ErrorCode.*;

import com.sparta.chat_test.chat.repository.ChatMessageRepository;
import com.sparta.chat_test.chat.repository.ChatRoomRepository;
import com.sparta.chat_test.chat.repository.UserRepository;
import com.sparta.chat_test.chat.repository.UserRoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRoomRepository userRoomRepository;
    private final UserRepository userRepository;
    private final ChatMessageRepository chatMessageRepository;

    public ChatRoomService(ChatRoomRepository chatRoomRepository,
                           UserRoomRepository userRoomRepository,
                           UserRepository userRepository, ChatMessageRepository chatMessageRepository) {

        this.chatRoomRepository = chatRoomRepository;
        this.userRoomRepository = userRoomRepository;
        this.userRepository = userRepository;
        this.chatMessageRepository = chatMessageRepository;
    }

    //채팅방 생성
    public ChatRoomResponseDto createRoom(ChatRoomRequestDto chatRoomRequestDto) {

        //  같은 이름의 채팅방 존재 여부
        if (chatRoomRepository.existsByRoomName(chatRoomRequestDto.getRoomName())) {
            throw new CustomException(400, CHATROOM_ALREADY_EXISTS, "이미 존재하는 채팅방 이름입니다.");
        }

        // 사용자 존재 여부
        User user = userRepository.findById(chatRoomRequestDto.getUserId())
                .orElseThrow(() -> new CustomException(404, USER_NOT_FOUND, "사용자가 없습니다."));

        // ChatRoom 엔티티 생성
        ChatRoom chatRoom = new ChatRoom(chatRoomRequestDto, user);

        // ChatRoom 저장
        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);

        // UserRoom 엔티티 생성
        UserRoom userRoom = new UserRoom(savedChatRoom, user);

        // UserRoom 저장
        userRoomRepository.save(userRoom);

        return new ChatRoomResponseDto(savedChatRoom);
    }

    // 채팅방 전체 조회
    public List<RoomListResponseDto> getListRoom() {

        List<RoomListResponseDto> roomList = new ArrayList<>();

        // 모든 채팅방을 생성일 기준으로 오름차순으로 가져옴
        List<ChatRoom> chatRooms = chatRoomRepository.findAllByOrderByCreatedDateAsc();

        // 각 채팅방에 대해 마지막 메시지를 스택으로 가져와서 RoomListResponseDto 객체를 생성
        for(ChatRoom chatRoom : chatRooms) {
            // 각 채팅방의 모든 메시지를 보낸 시간 기준으로 오름차순으로 가져옴
            List<ChatMessage> messageList = chatMessageRepository.findAllByChatRoomOrderBySendDateAsc(chatRoom);

            // 스택으로 메시지를 처리
            Stack<ChatMessage> messageStack = new Stack<>();
            messageStack.addAll(messageList);

            // 가장 최신 메시지
            ChatMessage lastMessage = null;
            if (!messageStack.isEmpty()) {
                lastMessage = messageStack.pop();
            }

            // 메세지가 있다면
            if (lastMessage != null) {
                LastMessageResponseDto lastMessageDto = new LastMessageResponseDto();
                lastMessageDto.setUserId(lastMessage.getUser().getUserId());
                lastMessageDto.setMessage(lastMessage.getMessage());

                RoomListResponseDto roomListResponseDto = new RoomListResponseDto(chatRoom, lastMessageDto);
                roomList.add(roomListResponseDto);
            } else {
                // 없다면 null 반환
                RoomListResponseDto roomListResponseDto = new RoomListResponseDto(chatRoom, null);
                roomList.add(roomListResponseDto);
            }
        }
        return roomList;
    }

    // 채팅방 삭제
    @Transactional
    public String deleteRoom(Long roomId, Long userId) {
        // 채팅방 존재 여부 확인
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(404, CHATROOM_NOT_FOUND, "채팅방이 존재하지 않습니다."));

        // 사용자가 채팅방에 속해 있는지 확인
        boolean userInRoom = false;
        for (UserRoom userRoom : chatRoom.getUserRooms()) {
            if (userRoom.getUser().getUserId().equals(userId)) {
                userInRoom = true;
                break;
            }
        }

        if (!userInRoom) {
            throw new CustomException(400, USER_NOT_IN_ROOM, "해당 사용자는 이 채팅방에 속해 있지 않아 삭제할 수 없습니다.");
        }

        // 채팅방과 관련된 UserRoom 엔티티들 삭제
        userRoomRepository.deleteByChatRoom(chatRoom);

        // 채팅방 삭제
        chatRoomRepository.delete(chatRoom);

        return "채팅방이 삭제되었습니다.";
    }

    //채팅방 참여
    public UserRoomResponseDto createUserRoom(UserRoomRequestDto userRoomRequestDto) {

        // 채팅방 존재 여부
        ChatRoom chatRoom = chatRoomRepository.findById(userRoomRequestDto.getRoomId())
                .orElseThrow(() -> new CustomException(404, CHATROOM_NOT_FOUND, "채팅방이 존재하지 않습니다."));

        // 사용자 존재 여부
        User user = userRepository.findById(userRoomRequestDto.getUserId())
                .orElseThrow(() -> new CustomException(400, USER_NOT_IN_ROOM, "해당 사용자는 이 채팅방에 속해 있지 않습니다."));

        // UserRoom 엔티티 생성
        UserRoom userRoom = new UserRoom(chatRoom, user);

        // UserRoom 저장
        UserRoom savedUserRoom = userRoomRepository.save(userRoom);

        return new UserRoomResponseDto(savedUserRoom);
    }
}
