package com.chat.main.application.member.usecase.command;

import com.chat.main.application.member.domain.Member;
import com.chat.main.application.member.repository.MemberDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class LoginUseCase {

    private final MemberDao memberDao;

    public LoginUseCase(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional
    public LoginSuccessResponse login(String loginId, String password) throws IllegalAccessException {
        Member member = memberDao.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        if (!member.getPassword().equals(password)) {
            throw new IllegalAccessException("패스워드가 틀렸습니다.");
        }

        return LoginSuccessResponse.of(
                member.getMemberId(),
                member.getName(),
                member.getNickName()
        );
    }
}
