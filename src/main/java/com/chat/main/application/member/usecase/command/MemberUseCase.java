package com.chat.main.application.member.usecase.command;

import com.chat.main.application.member.domain.Member;
import com.chat.main.application.member.repository.MemberDao;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MemberUseCase {

    private final MemberDao memberDao;
    private final PasswordEncoder passwordEncoder;

    public MemberUseCase(MemberDao memberDao,
                         PasswordEncoder passwordEncoder) {
        this.memberDao = memberDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void createMember(CreateMemberRequest request) {
        var member = Member.of(
                request.loginId(),
                request.name(),
                request.nickName(),
                request.phoneNumber(),
                passwordEncoder.encode(request.password())
        );

        memberDao.save(member);
    }


}
