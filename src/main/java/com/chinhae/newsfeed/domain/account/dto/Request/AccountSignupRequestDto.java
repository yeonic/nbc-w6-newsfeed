package com.chinhae.newsfeed.domain.account.dto.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

@Getter
public class AccountSignupRequestDto {

    @Email
    private String email;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*\\W)[^\\s]{8,16}$", message = "비밀번호는 8~16자, 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;
    private String username;
    private LocalDate birthDate;
}
