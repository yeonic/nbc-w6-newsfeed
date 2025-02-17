package com.chinhae.newsfeed.domain.account.service;

import com.chinhae.newsfeed.domain.account.dto.Request.UserLoginRequestDto;
import com.chinhae.newsfeed.domain.account.dto.Request.UserSignupRequestDto;
import com.chinhae.newsfeed.domain.account.dto.Response.UserLoginResponseDto;
import com.chinhae.newsfeed.domain.account.dto.Response.UserSignupResponsetDto;
import com.chinhae.newsfeed.domain.account.entity.User;
import com.chinhae.newsfeed.domain.account.repository.AccountRepository;
import com.chinhae.newsfeed.global.config.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserSignupResponsetDto save(UserSignupRequestDto requestDto) { // 회원가입
        if (accountRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("해당 이메일은 사용중입니다");
        }
        String encodePassword = passwordEncoder.encode(requestDto.getPassword());
        User user = new User(requestDto.getEmail(), encodePassword, requestDto.getUsername(), requestDto.getBirthDate());
        accountRepository.save(user);

        return new UserSignupResponsetDto(user.getEmail(), user.getUsername(), user.getCreated_at());
    }

    @Transactional
    public UserLoginResponseDto loginUser(UserLoginRequestDto requestDto) { // 로그인
        User user = accountRepository.findByEmail(requestDto.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("이메일이 같지않습니다.")
        );
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지않습니다.");
        }

        return new UserLoginResponseDto(user.getEmail(), user.getUsername(), user.getCreated_at());
    }
}
