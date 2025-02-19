-- 계정 테이블
create table account
(
    id         bigint       not null auto_increment,
    email      varchar(255) not null,
    password   varchar(80)  not null,
    name       varchar(20)  not null,
    birthdate  date,
    created_at datetime default current_timestamp,
    updated_at datetime default current_timestamp on update current_timestamp,

    primary key (id)
);

-- 프로필 테이블
create table profile
(
    id              bigint      not null auto_increment,
    account_id      bigint      null,
    nickname        varchar(25) not null,
    profile_img_url text,
    bio             varchar(160),
    friends_count   int      default 0,
    post_count      int      default 0,
    created_at      datetime default current_timestamp,
    updated_at      datetime default current_timestamp on update current_timestamp,

    primary key (id),
    constraint accounts_ibfk_1 foreign key (account_id) references account (id)
);

-- 게시글 테이블
create table post
(
    id            bigint        not null auto_increment,
    content       varchar(4500) not null,
    view_count    int      default 0,
    comment_count int      default 0,
    like_count    int      default 0,
    created_at    datetime default current_timestamp,
    updated_at    datetime default current_timestamp on update current_timestamp,

    primary key (id)
);

-- 댓글 테이블
create table comment
(
    id         bigint       not null auto_increment,
    profile_id bigint       not null,
    post_id    bigint       not null,
    content    varchar(600) not null,
    created_at datetime default current_timestamp,
    updated_at datetime default current_timestamp on update current_timestamp,

    primary key (id),
    constraint comments_ibfk_1 foreign key (profile_id) references profile (id),
    constraint comments_ibfk_2 foreign key (post_id) references post (id)
);


-- 친구 관계 테이블
create table friendship
(
    id              bigint not null auto_increment,
    from_profile_id bigint not null,
    to_profile_id   bigint not null,
    status          varchar(20) default 'PENDING',
    created_at      datetime    default current_timestamp,

    primary key (id),
    constraint friends_ibfk_1 foreign key (from_profile_id) references profile (id),
    constraint friends_ibfk_2 foreign key (to_profile_id) references profile (id)

);

-- 게시글 좋아요 테이블
create table post_like
(
    id         bigint not null auto_increment,
    profile_id bigint not null,
    post_id    bigint not null,

    primary key (id),
    constraint post_likes_ibfk_1 foreign key (profile_id) references profile (id),
    constraint post_likes_ibfk_2 foreign key (post_id) references post (id)
);