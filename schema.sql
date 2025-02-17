-- 계정 테이블
create table accounts
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
create table profiles
(
    id              bigint      not null auto_increment,
    account_id      bigint      not null,
    nickname        varchar(25) not null,
    profile_img_url text,
    bio             varchar(160),
    friends_count   int      default 0,
    post_count      int      default 0,
    birthdate       date,
    created_at      datetime default current_timestamp,
    updated_at      datetime default current_timestamp on update current_timestamp,

    primary key (id),
    foreign key (account_id) references accounts (id)
);

-- 게시글 테이블
create table posts
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
create table comments
(
    id         bigint       not null auto_increment,
    profile_id bigint       not null,
    post_id    bigint       not null,
    content    varchar(600) not null,
    created_at datetime default current_timestamp,
    updated_at datetime default current_timestamp on update current_timestamp,

    primary key (id),
    foreign key (profile_id) references profiles (id),
    foreign key (post_id) references posts (id)
);


-- 친구 관계 테이블
create table friends
(
    id              bigint not null auto_increment,
    from_profile_id bigint not null,
    to_profile_id   bigint not null,
    status          varchar(20) default 'pending',
    created_at      datetime    default current_timestamp,

    primary key (id),
    foreign key (from_profile_id) references profiles (id),
    foreign key (to_profile_id) references profiles (id)

);

-- 게시글 좋아요 테이블
create table post_likes
(
    id         bigint not null auto_increment,
    profile_id bigint not null,
    post_id    bigint not null,

    primary key (id),
    foreign key (profile_id) references profiles (id),
    foreign key (post_id) references posts (id)
);