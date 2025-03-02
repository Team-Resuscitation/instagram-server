package com.resuscitation.instagram.auth.service

import com.resuscitation.instagram.auth.UserPrincipal
import com.resuscitation.instagram.auth.oauth.OAuth2UserInfo
import com.resuscitation.instagram.auth.oauth.OAuth2UserInfoFactory
import com.resuscitation.instagram.user.domain.Provider
import com.resuscitation.instagram.user.domain.ProviderType
import com.resuscitation.instagram.user.domain.User
import com.resuscitation.instagram.user.repository.UserRepository
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import java.util.Locale

@Service
class CustomOAuth2UserService(
    private val userRepository: UserRepository,
) : DefaultOAuth2UserService() {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = super.loadUser(userRequest)

        // OAuth2 제공자 타입
        val providerType =
            ProviderType.valueOf(
                userRequest.clientRegistration.registrationId.uppercase(Locale.getDefault()),
            )
        // OAuth2 유저 정보 분석
        val userInfo: OAuth2UserInfo =
            OAuth2UserInfoFactory.getOAuth2UserInfo(
                providerType,
                oAuth2User.attributes,
            )
        // 제공자 정보
        val provider = Provider(providerType, userInfo.id)

        // 유저 정보 조회 또는 등록
        val user: User =
            userRepository.findByProviders(provider)
                ?: register(userInfo, provider)

        return UserPrincipal.create(user, oAuth2User.attributes)
    }

    private fun register(
        userInfo: OAuth2UserInfo,
        provider: Provider,
    ): User {
        val user =
            User(
                email = userInfo.email ?: throw IllegalArgumentException("Email not found."),
                nickname = userInfo.name ?: throw IllegalArgumentException("Name not found."),
                profileImage = userInfo.imageUrl,
                providers = mutableSetOf(provider),
            )
        return userRepository.save(user)
    }
}
