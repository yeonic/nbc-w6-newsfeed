package com.chinhae.newsfeed.domain.profile.entity;

import static com.chinhae.newsfeed.global.util.StringUtil.getOrDefaultEmpty;

import com.chinhae.newsfeed.domain.account.entity.Account;
import com.chinhae.newsfeed.domain.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Profile extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long id;

  @Size(max = 25)
  @NotNull
  @Column(nullable = false, length = 25)
  private String nickname;

  @Lob
  private String profileImgUrl;

  @Size(max = 160)
  @Column(length = 160)
  private String bio;

  @ColumnDefault("0")
  private Integer friendsCount;

  @ColumnDefault("0")
  private Integer postsCount;

  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account user;

  @Builder
  public Profile(String nickname, String profileImgUrl, String bio) {
    this.nickname = getOrDefaultEmpty(nickname);
    this.profileImgUrl = getOrDefaultEmpty(profileImgUrl);
    this.bio = getOrDefaultEmpty(bio);
    this.friendsCount = 0;
    this.postsCount = 0;
  }

  public void updateByForm(String nickname, String bio, String profileImgUrl) {
    this.nickname = nickname;
    this.bio = bio;
    this.profileImgUrl = profileImgUrl;
  }
}