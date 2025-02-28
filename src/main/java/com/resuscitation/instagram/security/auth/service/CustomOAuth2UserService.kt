package com.resuscitation.instagram.security.auth.service

import com.resuscitation.instagram.security.auth.domain.User
import com.resuscitation.instagram.security.auth.repository.SocialUserRepository
import com.resuscitation.instagram.security.auth.repository.UserRepository
import com.resuscitation.instagram.security.configuration.JwtProperties
import com.resuscitation.instagram.security.jwt.JwtProvider
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
@EnableConfigurationProperties(JwtProperties::class)
class CustomOAuth2UserService(
    private val socialUserRepository: SocialUserRepository,
    private val userRepository: UserRepository,
    private val jwtProvider: JwtProvider,
    private val jwtProperties: JwtProperties,
) {
    fun loadUser(user: OAuth2User): com.resuscitation.instagram.security.auth.dto.LoginResponseDto {
        val providerId = user.attributes["sub"] as String
        val nickname = user.attributes["nickname"] as String
        val socialUser = socialUserRepository.findByProviderId(providerId)
        if (socialUser == null) {
            userRepository.save(
                User(
                    nickname = nickname,
                ),
            )
        }
        return com.resuscitation.instagram.security.auth.dto.LoginResponseDto(
            accessToken =
                jwtProvider.generateToken(
                    userRepository.findByNickname(nickname),
                    jwtProperties.accessExpiration,
                ),
        )
    }
}
