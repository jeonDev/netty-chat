package com.chat.main.application.chat.domain;

import java.io.Serializable;
import java.util.Objects;

public class ChatRoomId implements Serializable {
    private Long chatRoomId;
    private Long memberId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatRoomId that)) return false;
        return Objects.equals(chatRoomId, that.chatRoomId) &&
                Objects.equals(memberId, that.chatRoomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatRoomId, memberId);
    }
}
