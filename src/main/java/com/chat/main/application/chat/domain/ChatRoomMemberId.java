package com.chat.main.application.chat.domain;

import java.io.Serializable;
import java.util.Objects;

public class ChatRoomMemberId implements Serializable {
    private Long chatRoomId;
    private Long memberId;

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
        return Objects.equals(chatRoomId, that.chatRoomId) &&
                Objects.equals(memberId, that.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatRoomId, memberId);
    }
}
