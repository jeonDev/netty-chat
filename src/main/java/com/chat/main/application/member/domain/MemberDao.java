package com.chat.main.application.member.domain;

import java.util.List;
import java.util.Optional;

public interface MemberDao {

    Member save(Member member);
    Optional<Member> findById(Long id);
    List<Member> findByAll();
}
