package com.chat.main.application.chat.endpoint;

import com.chat.main.application.chat.endpoint.request.ChatRoomCreateRequest;
import com.chat.main.application.chat.endpoint.request.ChatRoomInviteRequest;
import com.chat.main.application.chat.endpoint.response.ChatRoomListResponse;
import com.chat.main.application.chat.usecase.command.ChatRoomResponse;
import com.chat.main.application.chat.usecase.command.ChatRoomUseCase;
import com.chat.main.application.chat.usecase.query.ChatRoomSearchUseCase;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ChatEndpoint {

    private final ChatRoomSearchUseCase chatRoomSearchUseCase;
    private final ChatRoomUseCase chatRoomUseCase;

    public ChatEndpoint(ChatRoomSearchUseCase chatRoomSearchUseCase,
                        ChatRoomUseCase chatRoomUseCase) {
        this.chatRoomSearchUseCase = chatRoomSearchUseCase;
        this.chatRoomUseCase = chatRoomUseCase;
    }

    @PostMapping("/api/v1/chatRoom")
    public Long create(@Valid @RequestBody ChatRoomCreateRequest request) {
        ChatRoomResponse chatRoomResponse = chatRoomUseCase.create(request.getChatType(), 1L, request.getMemberIds());

        return chatRoomResponse.chatRoomId();
    }

    @PutMapping("/api/v1/chatRoom")
    public Long invite(@Valid @RequestBody ChatRoomInviteRequest request) {
        ChatRoomResponse chatRoomResponse = chatRoomUseCase.invite(request.getChatRoomId(), request.getMemberIds());

        return chatRoomResponse.chatRoomId();
    }

    @GetMapping("/api/v1/chatRoom/list")
    public List<ChatRoomListResponse> getChatRoomList() {
        return chatRoomSearchUseCase.getChatRoomList(1L);
    }
}
