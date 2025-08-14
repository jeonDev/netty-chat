package com.chat.main.application.member.infra.jpa;

import com.chat.main.application.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemberRepository extends JpaRepository<Member, Long> {
}
