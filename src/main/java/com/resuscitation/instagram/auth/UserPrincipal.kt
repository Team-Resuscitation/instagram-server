package com.resuscitation.instagram.auth

import com.resuscitation.instagram.user.domain.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.oauth2.core.oidc.OidcUserInfo
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.oauth2.core.user.OAuth2User

/**
 * 스프링 시큐리티 필터 통과용 사용자 정보 클래스
 */
class UserPrincipal(
    private val userId: String,
    private val password: String? = null,
    private val authorities: Collection<GrantedAuthority>? = null,
    private var attributes: Map<String?, Any?>? = null,
) : OAuth2User, UserDetails, OidcUser {
    override fun getName(): String {
        return userId
    }

    override fun getAttributes(): Map<String?, Any?> {
        return attributes!!
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities!!
    }

    override fun getPassword(): String {
        return password as String
    }

    override fun getUsername(): String {
        return userId
    }

    override fun getClaims(): MutableMap<String, Any>? {
        return null
    }

    override fun getUserInfo(): OidcUserInfo? {
        return null
    }

    override fun getIdToken(): OidcIdToken? {
        return null
    }

    companion object {
        fun create(
            user: User
        ): UserPrincipal {
            return UserPrincipal(
                userId = user.id.toString(),
                password = user.password,
                authorities = user.roles,
                attributes = mapOf()
            )
        }

        fun create(
            user: User,
            attributes: Map<String?, Any?>
        ): UserPrincipal {
            return UserPrincipal(
                userId = user.id.toString(),
                password = user.password,
                authorities = user.roles,
                attributes = attributes
            )
        }
    }
}
