package com.chat.main.application.member.repository;

import com.chat.main.application.member.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberDao {

    Member save(Member member);
    Optional<Member> findById(Long id);
    List<Member> findByAll();
    Optional<Member> findByLoginId(String loginId);
}
