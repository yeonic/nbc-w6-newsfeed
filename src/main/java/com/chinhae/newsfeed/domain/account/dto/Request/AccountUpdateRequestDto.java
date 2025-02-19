package com.chinhae.newsfeed.domain.account.dto.Request;

import com.chinhae.newsfeed.global.messages.ValidationConst;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class AccountUpdateRequestDto {

    private String currentPassword;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*\\W)[^\\s]{8,16}$", message = ValidationConst.WRONG_PASSWORD)
    private String newPassword;
}
