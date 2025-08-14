package com.chat.main.application.chat.infra.jpa;

import com.chat.main.application.chat.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaChatRepository extends JpaRepository<Chat, Long> {
}
