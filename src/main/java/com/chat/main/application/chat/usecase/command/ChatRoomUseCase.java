package com.chat.main.application.chat.usecase.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class ChatRoomUseCase {

    @Transactional
    public void chatRoomInvite(Long memberId, Long... requestMemberId) {

    }
}
