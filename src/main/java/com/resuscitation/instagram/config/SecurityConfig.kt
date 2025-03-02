package com.resuscitation.instagram.config


import com.resuscitation.instagram.auth.TokenAuthenticationFilter
import com.resuscitation.instagram.auth.TokenProvider
import com.resuscitation.instagram.auth.handler.OAuth2SuccessHandler
import com.resuscitation.instagram.auth.service.CustomOAuth2UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig(
    private val userDetailsService: UserDetailsService,
    private val oauth2UserDetailsService: CustomOAuth2UserService,
    private val oAuth2SuccessHandler: OAuth2SuccessHandler,
) {
    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        tokenProvider: TokenProvider,
    ): SecurityFilterChain {
        http.csrf { it.disable() }.authorizeHttpRequests { authorize ->
            authorize.anyRequest().permitAll()
        }.userDetailsService(userDetailsService).oauth2Login { oauth2Login ->
            oauth2Login.userInfoEndpoint { userInfoEndpoint ->
                userInfoEndpoint.userService(oauth2UserDetailsService)
            }.successHandler(oAuth2SuccessHandler)
        }.addFilterBefore(TokenAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }
}
