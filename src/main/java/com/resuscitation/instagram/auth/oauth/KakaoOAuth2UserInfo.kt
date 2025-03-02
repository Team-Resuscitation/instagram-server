package com.resuscitation.instagram.auth.oauth

class KakaoOAuth2UserInfo(
    attributes: Map<String, Any>,
) : OAuth2UserInfo(attributes) {
    override val id: String
        get() = attributes["id"].toString()

    override val name: String?
        get() {
            val properties = attributes["properties"] as Map<*, *>

            return properties["nickname"]?.toString()
        }

    override val email: String?
        get() {
            return attributes["kakao_account"]?.let {
                val account = it as Map<*, *>
                account["email"]?.toString()
            }
        }

    override val imageUrl: String?
        get() {
            val properties = attributes["properties"] as Map<*, *>

            return properties["profile_image"]?.toString()
        }
}
