package com.chinhae.newsfeed.domain.account.dto.Request;

import com.chinhae.newsfeed.global.messages.ValidationConst;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class AccountLoginRequestDto {

    @Pattern(regexp = "^^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = ValidationConst.EMAIL_IS_WRONG)
    private String email;
    private String password;
}
