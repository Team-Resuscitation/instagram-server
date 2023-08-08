package com.resuscitation.Instagram.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // JWT 사용을 위한 CSRF 해제
        http.csrf(csrf -> csrf.disable());

        // Spring Security Request 보안 설정
        // TODO: 로그인, 회원 가입을 제외한 모든 요청에 isAuthenticate 추가
        http.authorizeRequests().anyRequest().permitAll();

        return http.build();
    }
}
