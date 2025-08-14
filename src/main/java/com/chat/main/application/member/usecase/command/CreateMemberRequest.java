package com.chat.main.application.member.usecase.command;

public record CreateMemberRequest(
        String loginId,
        String name,
        String nickName,
        String password,
        String phoneNumber
) {

    public static CreateMemberRequest of(
            String loginId,
            String name,
            String nickName,
            String password,
            String phoneNumber
    ) {
        if (loginId == null || loginId.isEmpty()) {
            throw new IllegalArgumentException("ID를 입력하시오");
        }

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("이름을 입력하시오");
        }

        if (password == null || password.length() > 20 || password.length() < 8) {
            throw new IllegalArgumentException("패스워드를 입력하시오.(8~20)");
        }

        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new IllegalArgumentException("전화번호를 입력하시오.");
        }

        return new CreateMemberRequest(
                loginId,
                name,
                nickName,
                password,
                phoneNumber
        );
    }
}
