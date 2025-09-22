package com.chat.main.application.config.security;

import com.chat.main.application.member.domain.Member;
import com.chat.main.application.member.repository.MemberDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final MemberDao memberDao;

    public UserService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Member member = memberDao.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
        return UserDetail.builder()
                .id(member.getMemberId())
                .loginId(member.getLoginId())
                .auth("ROLE_USER")
                .password(member.getPassword())
                .build();
    }
}
