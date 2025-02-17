package com.chinhae.newsfeed.domain.account.service;

import com.chinhae.newsfeed.domain.account.dto.Request.UserLoginRequestDto;
import com.chinhae.newsfeed.domain.account.dto.Request.UserSignupRequestDto;
import com.chinhae.newsfeed.domain.account.dto.Response.UserLoginResponseDto;
import com.chinhae.newsfeed.domain.account.dto.Response.UserResponseDto;
import com.chinhae.newsfeed.domain.account.dto.Response.UserSignupResponsetDto;
import com.chinhae.newsfeed.domain.account.dto.Response.UserUpdateResponse;
import com.chinhae.newsfeed.domain.account.entity.User;
import com.chinhae.newsfeed.domain.account.repository.AccountRepository;
import com.chinhae.newsfeed.global.config.PasswordEncoder;
import com.chinhae.newsfeed.global.messages.LoginConst;
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
            throw new IllegalArgumentException(LoginConst.EMAIL_EXIST_MESSAGE);
        }
        String encodePassword = passwordEncoder.encode(requestDto.getPassword());
        User user = new User(requestDto.getEmail(), encodePassword, requestDto.getUsername(), requestDto.getBirthDate());
        accountRepository.save(user);

        return new UserSignupResponsetDto(user.getEmail(), user.getUsername(), user.getCreated_at());
    }

    @Transactional
    public UserLoginResponseDto loginUser(UserLoginRequestDto requestDto) { // 로그인
        User user = accountRepository.findByEmail(requestDto.getEmail()).orElseThrow(
                () -> new IllegalArgumentException(LoginConst.LOGIN_FAILED_MESSAGE)
        );
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())){
            throw new IllegalArgumentException(LoginConst.LOGIN_FAILED_MESSAGE);
        }

        return new UserLoginResponseDto(user.getEmail(), user.getUsername(), user.getCreated_at());
    }

    @Transactional(readOnly = true)
    public UserResponseDto findUser(Long userId) { // 사용자 조회
        User user = accountRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException(LoginConst.USER_NOTEXIST_MESSAGE)
        );
        return new UserResponseDto(user.getEmail(), user.getUsername());
    }

    @Transactional
    public void deleteUser(Long userId) { // 회원 탈퇴
        accountRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException(LoginConst.DELETE_FAILED_MESSAGE)
        );

        accountRepository.deleteById(userId);
    }

    @Transactional(readOnly = true)
    public UserUpdateResponse updateForm(Long usersId) { // 유저 수정 폼
        User user = accountRepository.findById(usersId).orElseThrow(
                () -> new IllegalArgumentException(LoginConst.USER_NOTEXIST_MESSAGE)
        );

        return new UserUpdateResponse(user.getEmail(), user.getUsername(), user.getBirthDate());
    }

}
