package com.chat.main.application.member.usecase.query;

import com.chat.main.application.member.repository.MemberDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
public class MemberSearchUseCase {

    private final MemberDao memberDao;

    public MemberSearchUseCase(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional(readOnly = true)
    public List<MemberDto> getMemberList() {
        return memberDao.findByAll().stream()
                .map(MemberDto::of)
                .toList();
    }
}
