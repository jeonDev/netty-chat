package com.chat.main.application.member.infra;

import com.chat.main.application.member.domain.Member;
import com.chat.main.application.member.domain.MemberDao;
import com.chat.main.application.member.infra.jpa.JpaMemberRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberDaoImpl implements MemberDao {
    private final JpaMemberRepository jpaMemberRepository;

    public MemberDaoImpl(JpaMemberRepository jpaMemberRepository) {
        this.jpaMemberRepository = jpaMemberRepository;
    }

    @Override
    public Member save(Member member) {
        return jpaMemberRepository.save(member);
    }

    @Override
    public Optional<Member> findById(Long id) {
        return jpaMemberRepository.findById(id);
    }

    @Override
    public List<Member> findByAll() {
        return jpaMemberRepository.findAll();
    }
}
