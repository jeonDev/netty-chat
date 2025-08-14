package com.chat.main.application.chat.usecase.command;

import com.chat.main.application.chat.domain.MessageType;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PROTECTED)
public record ChatSendResponse(
        MessageType messageType,
        Object message,
        Long sendMemberId
) {
}
