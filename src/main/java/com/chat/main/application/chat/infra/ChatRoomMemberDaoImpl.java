package com.chat.main.application.chat.infra;

import com.chat.main.application.chat.domain.ChatRoomMember;
import com.chat.main.application.chat.infra.jpa.JpaChatRoomMemberRepository;
import com.chat.main.application.chat.repository.ChatRoomMemberDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatRoomMemberDaoImpl implements ChatRoomMemberDao {

    private final JpaChatRoomMemberRepository jpaChatRoomMemberRepository;

    public ChatRoomMemberDaoImpl(JpaChatRoomMemberRepository jpaChatRoomMemberRepository) {
        this.jpaChatRoomMemberRepository = jpaChatRoomMemberRepository;
    }

    @Override
    public void saveAll(List<ChatRoomMember> chatRoomMembers) {
        jpaChatRoomMemberRepository.saveAll(chatRoomMembers);
    }
}
