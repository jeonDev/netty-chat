package com.chat.main.socket.handler;

import com.chat.main.application.chat.domain.MessageType;
import com.chat.main.socket.telegram.Chatting;
import com.chat.main.socket.telegram.Telegram;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReadHandler {

    public Telegram readToMakeTelegram(MessageType messageType, Object message) {
        var telegram = Chatting.builder()
                .messageType(messageType)
                .message(message)
                .build();

        log.info("Telegram : {}", telegram);

        return telegram;
    }
}
