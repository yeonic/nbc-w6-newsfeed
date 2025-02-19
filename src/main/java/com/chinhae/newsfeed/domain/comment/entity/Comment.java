package com.chinhae.newsfeed.domain.comment.entity;

import com.chinhae.newsfeed.domain.base.entity.BaseEntity;
import com.chinhae.newsfeed.temporary.post.entity.Post;
import com.chinhae.newsfeed.domain.profile.entity.Profile;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 600)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    public Comment(String content, Profile profile, Post post) {
        this.content = content;
        this.profile = profile;
        this.post = post;
    }

    public void update(String content) {
        this.content = content;
    }
}
