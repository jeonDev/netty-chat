package com.chat.main.application.member.usecase.command;

import com.chat.main.application.member.domain.Member;
import com.chat.main.application.member.domain.MemberDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MemberUseCase {

    private final MemberDao memberDao;

    public MemberUseCase(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional
    public void createMember(CreateMemberRequest request) {
        var member = Member.of(
                request.loginId(),
                request.name(),
                request.nickName(),
                request.phoneNumber(),
                request.password()
        );

        memberDao.save(member);
    }


}
