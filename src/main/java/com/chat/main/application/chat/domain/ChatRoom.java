package com.chat.main.application.chat.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "CHAT_ROOM")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHAT_ROOM_ID")
    private Long chatRoomId;

    @Enumerated(EnumType.STRING)
    @Column(name = "CHAT_TYPE")
    private ChatType chatType;

    @Column(name = "MEMBER_ID")
    private Long memberId;

    public ChatRoom(Long chatRoomId, ChatType chatType, Long memberId) {
        this.chatRoomId = chatRoomId;
        this.chatType = chatType;
        this.memberId = memberId;
    }

    public static ChatRoom of(
            ChatType chatType,
            Long memberId
    ) {
        return of(
                null,
                chatType,
                memberId
        );
    }

    public static ChatRoom of(
            Long chatRoomId,
            ChatType chatType,
            Long memberId
    ) {
        return new ChatRoom(
                chatRoomId,
                chatType,
                memberId
        );
    }
}
