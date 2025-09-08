package com.chat.main.application.chat.endpoint.response;

import com.chat.main.application.chat.domain.ChatRoomMember;

public record ChatRoomListResponse(
        Long chatRoomId
) {

    public static ChatRoomListResponse of(
            ChatRoomMember chatRoomMember
    ) {
        return new ChatRoomListResponse(chatRoomMember.getChatRoomId());
    }
}
