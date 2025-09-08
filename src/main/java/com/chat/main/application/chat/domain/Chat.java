package com.chat.main.application.chat.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "CHAT")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CHAT_ROOM_ID")
    private Long chatRoomId;

    @Column(name = "MEMBER_ID")
    private Long memberId;

    @Enumerated(EnumType.STRING)
    @Column(name = "MESSAGE_TYPE")
    private MessageType messageType;

    @Column(name = "MESSAGE")
    private String message;

    public static Chat of(
            Long chatRoomId,
            Long memberId,
            MessageType messageType,
            String message
    ) {
        return new Chat(
                null,
                chatRoomId,
                memberId,
                messageType,
                message
        );
    }

    private Chat(Long id,
                 Long chatRoomId,
                 Long memberId,
                 MessageType messageType,
                 String message
    ) {
        this.id = id;
        this.chatRoomId = chatRoomId;
        this.memberId = memberId;
        this.messageType = messageType;
        this.message = message;
        this.validFields();
    }

    private void validFields() {
        if (chatRoomId == null) {
            throw new IllegalArgumentException("채팅 방이 선택 되지 않았습니다.");
        }

        if (messageType == null) {
            throw new IllegalArgumentException("메시지 타입이 명확하지 않습니다.");
        }

        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("메시지를 입력하시오.");
        }

        if (memberId == null) {
            throw new IllegalArgumentException("보낼 대상이 없습니다.");
        }
    }
}
