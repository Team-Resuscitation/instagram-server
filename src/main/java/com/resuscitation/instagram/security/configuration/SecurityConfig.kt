package com.resuscitation.instagram.security.configuration

import com.resuscitation.instagram.security.filter.JwtFilter
import com.resuscitation.instagram.security.jwt.JwtProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration


@Configuration
class SecurityConfig {
    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity, jwtProvider: JwtProvider): SecurityFilterChain {
        http
            .csrf { it.disable() }

        http.authorizeHttpRequests { authorize ->
            authorize
                .requestMatchers(
                    "/h2-console/**",
                    "/swagger-ui/**",
                    "/swagger-resources/**",
                    "/v3/api-docs/**",
                    "/{nickname}",
                    "/**"
//                    "/api/v2/auth/instagram/register",
                )
                .permitAll()
                .anyRequest().authenticated()
        }

        // add jwt filter
//        http.addFilterBefore(JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter::class.java)

        // add oauth2 filter
//        http.addFilterBefore(JwtFilter(jwtProvider), OAuth2LoginAuthenticationFilter::class.java)


        // Deactivate FrameOptions for H2 console
        http.headers { headers ->
            headers.frameOptions { it.disable() }
        }

        // CORS
        http.cors { cors ->
            cors.configurationSource {
                CorsConfiguration().apply {
                    addAllowedOrigin("*")
                    addAllowedMethod("*")
                    addAllowedHeader("*")
                }
            }
        }

        return http.build()
    }
}