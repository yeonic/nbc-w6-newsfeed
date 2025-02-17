package com.chinhae.newsfeed.domain.account.entity;

import com.chinhae.newsfeed.domain.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "user")
@NoArgsConstructor
public class Account extends BaseEntity {
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

    public Account(String email, String password, String username, LocalDate birthDate) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.birthDate = birthDate;
    }

    public void update(String newPassword) {
        this.password = newPassword;
    }
}
