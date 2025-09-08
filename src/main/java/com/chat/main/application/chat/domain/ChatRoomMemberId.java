package com.chat.main.application.chat.domain;

import java.io.Serializable;
import java.util.Objects;

public class ChatRoomMemberId implements Serializable {
    private Long memberId;
    private Long chatRoomId;

    public ChatRoomMemberId() {
    }

    public ChatRoomMemberId(Long chatRoomId, Long memberId) {
        this.chatRoomId = chatRoomId;
        this.memberId = memberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatRoomMemberId that)) return false;
        return Objects.equals(memberId, that.memberId) &&
                Objects.equals(chatRoomId, that.chatRoomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, chatRoomId);
    }
}
