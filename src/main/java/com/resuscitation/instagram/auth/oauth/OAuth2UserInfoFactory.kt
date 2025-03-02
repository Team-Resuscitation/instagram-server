package com.resuscitation.instagram.auth.oauth

import com.resuscitation.instagram.user.domain.ProviderType

object OAuth2UserInfoFactory {
    fun getOAuth2UserInfo(
        providerType: ProviderType?,
        attributes: Map<String, Any>,
    ): OAuth2UserInfo {
        return when (providerType) {
            ProviderType.KAKAO -> KakaoOAuth2UserInfo(attributes)
            ProviderType.GOOGLE -> GoogleOAuth2UserInfo(attributes)
            ProviderType.GITHUB -> GitHubOAuth2UserInfo(attributes)
            else -> throw IllegalArgumentException("Invalid Provider Type.")
        }
    }
}
