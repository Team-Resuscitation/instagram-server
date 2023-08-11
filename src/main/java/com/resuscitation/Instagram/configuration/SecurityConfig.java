package com.resuscitation.Instagram.configuration;

import com.resuscitation.Instagram.jwt.JwtTokenProvider;
import com.resuscitation.Instagram.security.JwtTokenFilterConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // JWT 사용을 위한 CSRF 해제
        http.csrf(csrf -> csrf.disable());

        // Spring Security Request 보안 설정
        http.authorizeRequests()
                .requestMatchers("/h2-console/**",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/v3/api-docs/**",
                        "/user/login",
                        "/user/register")
                .permitAll()
                .anyRequest().authenticated();

        http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));

        return http.build();
    }
}
