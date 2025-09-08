package com.chat.main.application.chat.infra;

import com.chat.main.application.chat.domain.Chat;
import com.chat.main.application.chat.repository.ChatDao;
import com.chat.main.application.chat.infra.jpa.JpaChatRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ChatDaoImpl implements ChatDao {

    private final JpaChatRepository jpaChatRepository;

    public ChatDaoImpl(JpaChatRepository jpaChatRepository) {
        this.jpaChatRepository = jpaChatRepository;
    }

    @Override
    public Chat save(Chat chat) {
        return jpaChatRepository.save(chat);
    }

    @Override
    public Optional<Chat> findById(Long id) {
        return jpaChatRepository.findById(id);
    }
}
