package com.chinhae.newsfeed.domain.account.dto.Request;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class AccountLoginRequestDto {

    @Email
    private String email;

    private String password;
}
