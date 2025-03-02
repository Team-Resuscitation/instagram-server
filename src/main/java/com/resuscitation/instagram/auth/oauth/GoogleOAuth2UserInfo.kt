package com.resuscitation.instagram.auth.oauth

class GoogleOAuth2UserInfo(
    attributes: Map<String, Any>,
) : OAuth2UserInfo(attributes) {
    override val id: String
        get() = attributes["sub"].toString()

    override val name: String?
        get() = attributes["name"]?.toString()

    override val email: String?
        get() = attributes["email"]?.toString()

    override val imageUrl: String?
        get() = attributes["picture"]?.toString()
}
