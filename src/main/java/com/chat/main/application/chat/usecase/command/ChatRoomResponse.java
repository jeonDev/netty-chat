package com.chat.main.application.chat.usecase.command;

public record ChatRoomResponse(
        Long chatRoomId
) {
    public static ChatRoomResponse of(Long chatRoomId) {
        return new ChatRoomResponse(chatRoomId);
    }
}
