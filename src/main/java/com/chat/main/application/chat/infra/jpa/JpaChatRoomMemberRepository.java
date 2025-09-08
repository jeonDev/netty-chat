package com.chat.main.application.chat.infra.jpa;

import com.chat.main.application.chat.domain.ChatRoomMember;
import com.chat.main.application.chat.domain.ChatRoomMemberId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaChatRoomMemberRepository extends JpaRepository<ChatRoomMember, ChatRoomMemberId> {
    List<ChatRoomMember> findByMemberIdAndDeleteYn(Long memberId, boolean deleteYn);
}
