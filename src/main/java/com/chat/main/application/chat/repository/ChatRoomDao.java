package com.chat.main.application.chat.repository;

import com.chat.main.application.chat.domain.ChatRoom;

import java.util.List;
import java.util.Optional;

public interface ChatRoomDao {
    ChatRoom save(ChatRoom chatRoom);
    void saveAll(List<ChatRoom> chatRoomList);
    List<ChatRoom> findByMemberId(Long memberId);
    Optional<ChatRoom> findByChatRoomByChatTypeAndTwoMembers(Long sendMemberId, Long targetMemberId);

    Long createChatRoomId();
}
