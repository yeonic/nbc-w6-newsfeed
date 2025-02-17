package com.chinhae.newsfeed.domain.profile.repository;

import com.chinhae.newsfeed.domain.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

}
