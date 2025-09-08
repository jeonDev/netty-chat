package com.chat.main.application.chat.infra;

import com.chat.main.application.chat.domain.ChatRoom;
import com.chat.main.application.chat.repository.ChatRoomDao;
import com.chat.main.application.chat.domain.ChatType;
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
    public void saveAll(List<ChatRoom> chatRoomList) {
        // 건이 많지 않을 것이라 그냥 saveAll
        jpaChatRoomRepository.saveAll(chatRoomList);
    }

    @Override
    public List<ChatRoom> findByMemberId(Long memberId) {
        return jpaChatRoomRepository.findByMemberId(memberId);
    }

    @Override
    public Optional<ChatRoom> findByChatRoomByChatTypeAndTwoMembers(Long sendMemberId, Long targetMemberId) {
        return jpaChatRoomRepository.findByChatRoomByChatTypeAndTwoMembers(ChatType.DM, sendMemberId, targetMemberId);
    }
}
