package com.chinhae.newsfeed.domain.account.service;

import com.chinhae.newsfeed.domain.account.dto.Request.AccountLoginRequestDto;
import com.chinhae.newsfeed.domain.account.dto.Request.AccountSignupRequestDto;
import com.chinhae.newsfeed.domain.account.dto.Request.AccountUpdateRequestDto;
import com.chinhae.newsfeed.domain.account.dto.Response.*;
import com.chinhae.newsfeed.domain.account.entity.Account;
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
    public AccountSignupResponsetDto save(AccountSignupRequestDto requestDto) { // 회원가입
        if (accountRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException(LoginConst.EMAIL_EXIST_MESSAGE);
        }
        String encodePassword = passwordEncoder.encode(requestDto.getPassword());
        Account user = new Account(requestDto.getEmail(), encodePassword, requestDto.getUsername(), requestDto.getBirthDate());
        accountRepository.save(user);

        return new AccountSignupResponsetDto(user.getEmail(), user.getUsername(), user.getCreated_at());
    }

    @Transactional
    public AccountLoginResponseDto loginUser(AccountLoginRequestDto requestDto) { // 로그인
        Account user = accountRepository.findByEmail(requestDto.getEmail()).orElseThrow(
                () -> new IllegalArgumentException(LoginConst.LOGIN_FAILED_MESSAGE)
        );
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())){
            throw new IllegalArgumentException(LoginConst.LOGIN_FAILED_MESSAGE);
        }

        return new AccountLoginResponseDto(user.getEmail(), user.getUsername(), user.getCreated_at());
    }

    @Transactional(readOnly = true)
    public AccountResponseDto findUser(Long userId) { // 사용자 조회
        Account user = accountRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException(LoginConst.USER_NOTEXIST_MESSAGE)
        );

        return new AccountResponseDto(user.getEmail(), user.getUsername());
    }

    @Transactional
    public void deleteUser(Long userId) { // 회원 탈퇴
        accountRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException(LoginConst.DELETE_FAILED_MESSAGE)
        );

        accountRepository.deleteById(userId);
    }

    @Transactional(readOnly = true)
    public AccountUpdateFormResponse updateForm(Long usersId) { // 계정 정보 수정 폼
        Account user = accountRepository.findById(usersId).orElseThrow(
                () -> new IllegalArgumentException(LoginConst.USER_NOTEXIST_MESSAGE)
        );

        return new AccountUpdateFormResponse(user.getEmail(), user.getUsername(), user.getBirthDate());
    }

    @Transactional
    public AccountUpdateResponseDto update(Long userId, AccountUpdateRequestDto requestDto) { // 계정 정보 수정
        Account user = accountRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException(LoginConst.USER_NOTEXIST_MESSAGE)
        );
        if (!passwordEncoder.matches(requestDto.getCurrentPassword(),user.getPassword())){
            throw new IllegalArgumentException(LoginConst.PASSWORD_NOT_MATCH);
        }
        user.update(requestDto.getNewPassword());

        return new AccountUpdateResponseDto(user.getEmail(), user.getUsername(), user.getCreated_at(), user.getUpdated_at());
    }
}
