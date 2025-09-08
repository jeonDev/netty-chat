package com.chat.main.application.chat.usecase.query;

import com.chat.main.application.chat.endpoint.response.ChatRoomListResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChatRoomSearchUseCase {

    public List<ChatRoomListResponse> getChatRoomList(Long memberId) {
        return List.of();
    }
}
