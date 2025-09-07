package com.chat.main.application.chat.usecase.command;

import com.chat.main.application.chat.domain.Chat;
import com.chat.main.application.chat.domain.ChatDao;
import com.chat.main.application.chat.domain.MessageType;
import com.chat.main.application.member.domain.MemberDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class ChatSendUseCase {
    private final ChatDao chatDao;
    private final MemberDao memberDao;

    public ChatSendUseCase(ChatDao chatDao,
                           MemberDao memberDao) {
        this.chatDao = chatDao;
        this.memberDao = memberDao;
    }

    @Transactional
    public ChatSendResponse messageSave(MessageType messageType,
                                        Long chatRoomId,
                                        String message,
                                        Long memberId
    ) {
        log.info("[Chat] send({}, {}, {}, {})", messageType, chatRoomId, message, memberId);

        // 1. 사용자 검증
        memberDao.findById(memberId)
                .orElseThrow();

        // 2. 메시지 전송
        Chat chat = this.createChat(messageType, chatRoomId, message, memberId);

        // 3. 메시지 처리
        this.messageAfterProcess(chat);

        return ChatSendResponse.builder()
                .messageType(chat.getMessageType())
                .message(chat.getMessage())
                .sendMemberId(chat.getMemberId())
                .build();
    }

    private Chat createChat(MessageType messageType, Long chatRoomId, String message, Long memberId) {
        return chatDao.save(
                Chat.of(
                        memberId,
                        chatRoomId,
                        messageType,
                        message
                )
        );
    }

    private void messageAfterProcess(Chat chat) {
        switch (chat.getMessageType()) {
            case IMAGE -> this.upload(chat.getMessage());
            default -> {}
        }
    }

    private void upload(Object message) {
        log.info("[Chat] 이미지 업로드중... >>>>>>");
    }
}
