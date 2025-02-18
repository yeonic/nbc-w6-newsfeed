package com.chinhae.newsfeed.domain.account.dto.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class AccountLoginRequestDto {

    @Pattern(regexp = "^^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일을 입력하세요.")
    private String email;

    private String password;
}
