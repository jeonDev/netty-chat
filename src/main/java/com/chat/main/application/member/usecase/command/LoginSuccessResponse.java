package com.chat.main.application.member.usecase.command;

public record LoginSuccessResponse(
        Long memberId,
        String name,
        String nickName
) {
    public static LoginSuccessResponse of(
            Long memberId,
            String name,
            String nickName
    ) {
        return new LoginSuccessResponse(memberId, name, nickName);
    }
}
