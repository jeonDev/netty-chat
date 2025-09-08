package com.chat.main.application.chat.repository;

import com.chat.main.application.chat.domain.ChatRoomMember;

import java.util.List;

public interface ChatRoomMemberDao {
    void saveAll(List<ChatRoomMember> chatRoomMembers);

    List<ChatRoomMember> findByMemberId(Long memberId);
}
