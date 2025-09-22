package com.chat.main.application.member.usecase.command;

import com.chat.main.application.config.security.JwtProvider;
import com.chat.main.application.member.domain.Member;
import com.chat.main.application.member.repository.MemberDao;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class LoginUseCase {

    private final MemberDao memberDao;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public LoginUseCase(MemberDao memberDao,
                        JwtProvider jwtProvider,
                        PasswordEncoder passwordEncoder) {
        this.memberDao = memberDao;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public LoginSuccessResponse login(String loginId, String password) throws IllegalAccessException {
        Member member = memberDao.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new IllegalAccessException("패스워드가 틀렸습니다.");
        }

        String token = jwtProvider.generateIdentificationInfo(member.getMemberId());

        return LoginSuccessResponse.of(
                member.getMemberId(),
                member.getName(),
                member.getNickName(),
                token
        );
    }
}
