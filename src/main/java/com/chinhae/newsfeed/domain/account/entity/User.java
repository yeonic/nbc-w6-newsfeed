package com.chinhae.newsfeed.domain.account.entity;

import com.chinhae.newsfeed.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "user")
@NoArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String email; // 이메일

    @Column(nullable = false, length = 80)
    private String password; // 비밀번호

    @Column(nullable = false, length = 20) // 한글 기준 최대 6자
    private String username; // 사용자 이름

    @Column
    private LocalDate birthDate; // 생년월일

    @Column
    private String bio; // 자기소개

    @Column
    private String profileImgUrl; // 사용자 프로필 url

    public User(String email, String password, String username, LocalDate birthDate) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.birthDate = birthDate;
    }

    public void update(String newPassword, String bio, String profileImgUrl) {
        this.password = newPassword;
        this.bio = bio;
        this.profileImgUrl = profileImgUrl;
    }
}
