package com.chat.main.application.chat.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(ChatRoomMemberId.class)
@Entity
@Table(name = "CHAT_ROOM_MEMBER")
public class ChatRoomMember {

    @Id
    @Column(name = "MEMBER_ID")
    private Long memberId;

    @Id
    @Column(name = "CHAT_ROOM_ID")
    private Long chatRoomId;

    @Column(name = "DELETE_YN", nullable = false)
    private boolean deleteYn;

    public ChatRoomMember(Long chatRoomId, Long memberId) {
        this.chatRoomId = chatRoomId;
        this.memberId = memberId;
        this.deleteYn = false;
    }

    public static ChatRoomMember of(
            Long chatRoomId,
            Long memberId
    ) {
        return new ChatRoomMember(
                chatRoomId,
                memberId
        );
    }
}

