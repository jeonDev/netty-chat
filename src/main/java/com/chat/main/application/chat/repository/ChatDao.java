package com.chat.main.application.chat.repository;

import com.chat.main.application.chat.domain.Chat;

import java.util.Optional;

public interface ChatDao {

    Chat save(Chat chat);
    Optional<Chat> findById(Long id);
}
