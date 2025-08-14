package com.chat.main.application.member.usecase.query;

import com.chat.main.application.member.domain.Member;

public record MemberDto(
        Long memberId,
        String name,
        String nickName
) {

    public static MemberDto of(Member member) {
        return new MemberDto(
                member.getMemberId(),
                member.getName(),
                member.getNickName()
        );
    }
}
