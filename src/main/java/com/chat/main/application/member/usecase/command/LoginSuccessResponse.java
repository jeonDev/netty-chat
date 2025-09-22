package com.chat.main.application.member.usecase.command;

public record LoginSuccessResponse(
        Long memberId,
        String name,
        String nickName,
        String token
) {
    public static LoginSuccessResponse of(
            Long memberId,
            String name,
            String nickName,
            String token
    ) {
        return new LoginSuccessResponse(memberId, name, nickName, token);
    }
}
