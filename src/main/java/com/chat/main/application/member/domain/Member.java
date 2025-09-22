package com.chat.main.application.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "MEMBER")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long memberId;

    @Column(name = "LOGIN_ID", length = 20, nullable = false, unique = true)
    public String loginId;

    @Column(name = "NAME", length = 20, nullable = false)
    private String name;

    @Column(name = "NICKNAME", length = 100)
    private String nickName;

    @Column(name = "PHONE_NUMBER", length = 11, nullable = false)
    private String phoneNumber;

    @Column(name = "PASSWORD", length = 255, nullable = false)
    private String password;

    public static Member of(
            String loginId,
            String name,
            String nickName,
            String phoneNumber,
            String password
    ) {
        return new Member(
                null,
                loginId,
                name,
                nickName,
                phoneNumber,
                password
        );
    }

    public Member(Long memberId, String loginId, String name, String nickName, String phoneNumber, String password) {
        this.memberId = memberId;
        this.loginId = loginId;
        this.name = name;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.validFields();
    }

    private void validFields() {
        if (this.name == null || this.name.isEmpty()) {
            throw new IllegalArgumentException("이름을 입력하시오.");
        }

        if (this.phoneNumber == null || this.phoneNumber.isEmpty()) {
            throw new IllegalArgumentException("전화번호를 입력하시오.");
        }
    }
}
