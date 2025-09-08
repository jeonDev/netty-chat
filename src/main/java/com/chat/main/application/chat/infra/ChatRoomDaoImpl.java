package com.chat.main.application.chat.infra;

import com.chat.main.application.chat.domain.ChatRoom;
import com.chat.main.application.chat.repository.ChatRoomDao;
import com.chat.main.application.chat.domain.ChatType;
import com.chat.main.application.chat.infra.jpa.JpaChatRoomRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ChatRoomDaoImpl implements ChatRoomDao {

    private final JpaChatRoomRepository jpaChatRoomRepository;

    public ChatRoomDaoImpl(JpaChatRoomRepository jpaChatRoomRepository) {
        this.jpaChatRoomRepository = jpaChatRoomRepository;
    }

    @Override
    public ChatRoom save(ChatRoom chatRoom) {
        return jpaChatRoomRepository.save(chatRoom);
    }

    @Override
    public Optional<ChatRoom> findById(Long chatRoomId) {
        return jpaChatRoomRepository.findById(chatRoomId);
    }

    @Override
    public Optional<ChatRoom> findByChatRoomByChatTypeAndTwoMembers(Long sendMemberId, Long targetMemberId) {
        return jpaChatRoomRepository.findByChatRoomByChatTypeAndTwoMembers(ChatType.DM, sendMemberId, targetMemberId);
    }

}
