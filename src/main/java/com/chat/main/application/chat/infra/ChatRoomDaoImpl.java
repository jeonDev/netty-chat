package com.chat.main.application.chat.infra;

import com.chat.main.application.chat.domain.ChatRoom;
import com.chat.main.application.chat.domain.ChatRoomDao;
import com.chat.main.application.chat.infra.jpa.JpaChatRoomRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    public Optional<ChatRoom> findById(Long id) {
        return jpaChatRoomRepository.findById(id);
    }

    @Override
    public List<ChatRoom> findByMemberId(Long memberId) {
        return jpaChatRoomRepository.findByMemberId(memberId);
    }
}
