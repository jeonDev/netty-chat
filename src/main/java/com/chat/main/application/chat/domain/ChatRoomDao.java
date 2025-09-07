package com.chat.main.application.chat.domain;

import java.util.List;
import java.util.Optional;

public interface ChatRoomDao {
    ChatRoom save(ChatRoom chatRoom);

    Optional<ChatRoom> findById(Long id);
    List<ChatRoom> findByMemberId(Long memberId);
}
