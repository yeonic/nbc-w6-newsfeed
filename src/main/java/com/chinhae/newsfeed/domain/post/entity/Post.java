package com.chinhae.newsfeed.domain.post.entity;

import com.chinhae.newsfeed.domain.account.entity.Account;
import com.chinhae.newsfeed.domain.base.BaseEntity;
import com.chinhae.newsfeed.domain.profile.entity.Profile;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;


@Getter
@Entity
@NoArgsConstructor
public class Post extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @ColumnDefault("0")
    @Column
    private Integer viewCount;

    @ColumnDefault("0")
    @Column
    private Integer commentCount;

    @ColumnDefault("0")
    @Column
    private Integer likeCount;


    //관계 Mapping
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    public Post(Profile profile, String content) {
        this.profile = profile;
        this.content = content;
    }

    public void update(String content) {
        this.content = content;
    }
}
