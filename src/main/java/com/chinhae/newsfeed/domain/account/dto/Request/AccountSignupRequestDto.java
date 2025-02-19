package com.chinhae.newsfeed.domain.account.dto.Request;

import com.chinhae.newsfeed.global.messages.ValidationConst;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

@Getter
public class AccountSignupRequestDto {

    @Pattern(regexp = "^^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = ValidationConst.EMAIL_IS_WRONG)
    private String email;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*\\W)[^\\s]{8,16}$", message = ValidationConst.WRONG_PASSWORD)
    private String password;
    private String username;
    private LocalDate birthDate;
}
