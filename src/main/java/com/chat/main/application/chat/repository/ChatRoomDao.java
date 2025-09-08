package com.chat.main.application.chat.repository;

import com.chat.main.application.chat.domain.ChatRoom;

import java.util.Optional;

public interface ChatRoomDao {
    ChatRoom save(ChatRoom chatRoom);
    Optional<ChatRoom> findById(Long chatRoomId);
    Optional<ChatRoom> findByChatRoomByChatTypeAndTwoMembers(Long sendMemberId, Long targetMemberId);
}
