package com.chat.main.application.member.endpoint;

import com.chat.main.application.member.usecase.query.MemberDto;
import com.chat.main.application.member.usecase.query.MemberSearchUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MemberEndpoint {
    private final MemberSearchUseCase memberSearchUseCase;

    public MemberEndpoint(MemberSearchUseCase memberSearchUseCase) {
        this.memberSearchUseCase = memberSearchUseCase;
    }

    @GetMapping("/api/v1/member/list")
    public List<MemberDto> getMemberList() {
        return memberSearchUseCase.getMemberList();
    }
}
