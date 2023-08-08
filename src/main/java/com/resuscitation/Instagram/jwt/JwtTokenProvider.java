package com.resuscitation.Instagram.jwt;

import com.resuscitation.Instagram.user.entity.UserEntity;
import com.resuscitation.Instagram.user.entity.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {


    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length}")
    private long validityInMilliseconds;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }


    public String createToken(UserEntity userEntity, List<UserRole> userRoles) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        // 쿠키 이름
        Claims claims = Jwts.claims().setSubject("내가 만든 쿠키~ 너를 위해 구웠지");

        // Spring Security HasRole 부분을 위한 부분
        claims.put("auth", userRoles.stream().map(s -> new SimpleGrantedAuthority(s.getAuthority()))
                .filter(Objects::nonNull).collect(Collectors.toList()));
        // 사용자 Idx
        claims.put("uid", userEntity.getIdx());

        // 사용자 이메일
        claims.put("email", userEntity.getEmail());

        return Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS256, secretKey)//
                .compact();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Long getUid(HttpServletRequest req) {

        String token = resolveToken(req);

        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .get("uid", Long.class);
    }

}
