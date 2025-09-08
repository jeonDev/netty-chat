package com.chat.main.application.chat.usecase.query;

import com.chat.main.application.chat.endpoint.response.ChatRoomListResponse;
import com.chat.main.application.chat.repository.ChatRoomMemberDao;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChatRoomSearchUseCase {

    private final ChatRoomMemberDao chatRoomMemberDao;

    public ChatRoomSearchUseCase(ChatRoomMemberDao chatRoomMemberDao) {
        this.chatRoomMemberDao = chatRoomMemberDao;
    }

    public List<ChatRoomListResponse> getChatRoomList(Long memberId) {
        return chatRoomMemberDao.findByMemberId(memberId).stream()
                .map(ChatRoomListResponse::of)
                .toList();
    }
}
