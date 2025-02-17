package com.chinhae.newsfeed.account.service;

import com.chinhae.newsfeed.account.dto.Request.UserLoginRequestDto;
import com.chinhae.newsfeed.account.dto.Request.UserSignupRequestDto;
import com.chinhae.newsfeed.account.dto.Response.UserLoginResponseDto;
import com.chinhae.newsfeed.account.dto.Response.UserSignupResponsetDto;
import com.chinhae.newsfeed.account.entity.User;
import com.chinhae.newsfeed.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public UserSignupResponsetDto save(UserSignupRequestDto requestDto) { // 회원가입
        User user = new User(requestDto.getEmail(), requestDto.getPassword(), requestDto.getUsername(), requestDto.getBirthDate());
        accountRepository.save(user);

        return new UserSignupResponsetDto(user.getEmail(), user.getUsername(), user.getCreated_at());
    }

    @Transactional
    public UserLoginResponseDto loginUser(UserLoginRequestDto requestDto) { // 로그인
        User user = accountRepository.findByEmail(requestDto.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("이메일이 같지않습니다.")
        );

        return new UserLoginResponseDto(user.getEmail(), user.getUsername(), user.getCreated_at());
    }
}
