package com.chat.main.application.member.endpoint.request;

import com.chat.main.application.member.usecase.command.CreateMemberRequest;
import lombok.Getter;

@Getter
public class SignUpRequest {
    private String loginId;
    private String name;
    private String nickName;
    private String phoneNumber;
    private String password;

    public CreateMemberRequest toRequest() {
        return CreateMemberRequest.of(
                this.loginId,
                this.name,
                this.nickName,
                this.password,
                this.phoneNumber
        );
    }
}
