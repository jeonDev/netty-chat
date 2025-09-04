package com.chat.main.application.member.endpoint;

import com.chat.main.application.member.endpoint.request.LoginRequest;
import com.chat.main.application.member.endpoint.request.SignUpRequest;
import com.chat.main.application.member.endpoint.response.LoginResponse;
import com.chat.main.application.member.usecase.command.LoginSuccessResponse;
import com.chat.main.application.member.usecase.command.LoginUseCase;
import com.chat.main.application.member.usecase.command.MemberUseCase;
import com.chat.main.application.member.usecase.query.MemberDto;
import com.chat.main.application.member.usecase.query.MemberSearchUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MemberEndpoint {
    private final MemberSearchUseCase memberSearchUseCase;
    private final MemberUseCase memberUseCase;
    private final LoginUseCase loginUseCase;

    public MemberEndpoint(MemberSearchUseCase memberSearchUseCase,
                          MemberUseCase memberUseCase,
                          LoginUseCase loginUseCase) {
        this.memberSearchUseCase = memberSearchUseCase;
        this.memberUseCase = memberUseCase;
        this.loginUseCase = loginUseCase;
    }

    @GetMapping("/api/v1/member/list")
    public List<MemberDto> getMemberList() {
        return memberSearchUseCase.getMemberList();
    }

    @PostMapping("/api/v1/member")
    public void join(@RequestBody SignUpRequest request) {
        memberUseCase.createMember(request.toRequest());
    }

    @PostMapping("/api/v1/login")
    public LoginResponse login(@RequestBody LoginRequest request) throws IllegalAccessException {
        LoginSuccessResponse response = loginUseCase.login(request.getLoginId(), request.getPassword());

        return LoginResponse.of(response);
    }
}
