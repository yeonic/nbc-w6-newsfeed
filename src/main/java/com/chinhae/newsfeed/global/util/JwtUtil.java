package com.chinhae.newsfeed.global.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private final Key key;
    private final long expirationTime;

    public JwtUtil(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") long expirationTime) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationTime = expirationTime;
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email) // 이메일주소로 서브젝트를 만듬.
                .setIssuedAt(new Date()) // 토큰 발행 일시
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // 만료일자
                .signWith(key, SignatureAlgorithm.HS256) // hs256 암호화로 사인하겠다.
                .compact(); // 생성
    }

    public String extractEmail(String token) { // 사인된 키를 가지고 바디에 있는 서브젝트를 가져오겠다.
        return Jwts.parserBuilder()
                .setSigningKey(key) // 사인 키를 검증
                .build()
                .parseClaimsJws(token) // 바디영역을 클레임(페이로드)
                .getBody() // 바디값을 가져오고
                .getSubject(); // 서브젝트를 가져온다.
    }

    public boolean validateToken(String token) { // 토큰을 검증
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}

