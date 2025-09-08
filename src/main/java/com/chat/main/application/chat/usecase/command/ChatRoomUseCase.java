package com.chat.main.application.chat.usecase.command;

import com.chat.main.application.chat.domain.ChatRoom;
import com.chat.main.application.chat.domain.ChatRoomDao;
import com.chat.main.application.chat.domain.ChatType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Component
public class ChatRoomUseCase {

    private final ChatRoomDao chatRoomDao;

    public ChatRoomUseCase(ChatRoomDao chatRoomDao) {
        this.chatRoomDao = chatRoomDao;
    }

    @Transactional
    public ChatRoomResponse create(ChatType chatType,
                                   Long memberId,
                                   Long... memberIds
    ) {
        // 1. 개인 채팅방
        if (chatType == ChatType.DM) {
            if (memberIds.length != 1) throw new IllegalArgumentException("개인 채팅방은 한 명만 초대 가능합니다.");

            // 1-1. 기존 방 존재 할 경우 기존 방 정보 응답.
            Optional<ChatRoom> optionalChatRoom = chatRoomDao.findByChatRoomByChatTypeAndTwoMembers(memberId, memberIds[0]);
            if (optionalChatRoom.isPresent()) {
                return ChatRoomResponse.of(
                        optionalChatRoom.get().getChatRoomId()
                );
            }
        }

        // 2. 채팅방 생성 및 초대
        var chatRoom = chatRoomDao.save(ChatRoom.of(
                chatType,
                memberId
        ));

        this.chatRoomInvite(chatType, chatRoom.getChatRoomId(), memberIds);

        return ChatRoomResponse.of(
                chatRoom.getChatRoomId()
        );
    }

    @Transactional
    public ChatRoomResponse invite(ChatType chatType,
                       Long chatRoomId,
                       Long... memberIds
    ) {
        // 1. DM 방 초대 불가
        if (chatType == ChatType.DM) {
            throw new IllegalArgumentException("개인 채팅방은 한 명만 대화 가능합니다.");
        }

        // 2. 사용자 초대
        this.chatRoomInvite(chatType, chatRoomId, memberIds);

        return ChatRoomResponse.of(
                chatRoomId
        );
    }

    private void chatRoomInvite(ChatType chatType, Long chatRoomId, Long[] memberIds) {
        var chatRoomList = Arrays.stream(memberIds)
                .map(item -> ChatRoom.of(chatRoomId, chatType, item))
                .toList();
        chatRoomDao.saveAll(chatRoomList);
    }
}
