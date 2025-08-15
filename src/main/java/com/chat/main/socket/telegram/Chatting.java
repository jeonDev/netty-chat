package com.chat.main.socket.telegram;

import com.chat.main.application.chat.domain.MessageType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Chatting implements Telegram {
    private final MessageType messageType;
    private final Object message;

    @Override
    public String parse() {
        return switch (messageType) {
            case MESSAGE -> this.messageParse();
            case IMAGE -> this.imageParse();
            default -> throw new IllegalArgumentException("Message Type Empty");
        };
    }

    private String messageParse() {
        return (String) this.message;
    }

    private String imageParse() {
        // TODO:
        return "";
    }
}
