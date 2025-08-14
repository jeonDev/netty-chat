package com.chat.main.application.chat.domain;

import java.util.Optional;

public interface ChatDao {

    Chat save(Chat chat);
    Optional<Chat> findById(Long id);
}
