package com.chinhae.newsfeed.domain.account.repository;

import com.chinhae.newsfeed.domain.account.entity.Account;
import org.hibernate.annotations.processing.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);

//    boolean existsByEmail(String email);

    @Query(value = "SELECT COUNT(email) FROM user WHERE email = ?", nativeQuery = true)
    Long existsByEmail(String email);

}
