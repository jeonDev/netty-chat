package com.chat.main.application.chat.infra.jpa;

import com.chat.main.application.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findByMemberId(Long MemberId);
}
