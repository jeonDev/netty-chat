package com.chat.main.application.chat.endpoint.request;

import com.chat.main.application.chat.domain.ChatType;
import lombok.Getter;

@Getter
public class ChatRoomInviteRequest {
    private ChatType chatType;
    private Long chatRoomId;
    private Long[] memberIds;
}
