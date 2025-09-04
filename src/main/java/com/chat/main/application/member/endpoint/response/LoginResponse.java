package com.chat.main.application.member.endpoint.response;

import com.chat.main.application.member.usecase.command.LoginSuccessResponse;

public record LoginResponse(
        Long memberId,
        String name,
        String nickName
) {

    public static LoginResponse of(LoginSuccessResponse response) {
        return new LoginResponse(response.memberId(), response.name(), response.nickName());
    }
}
