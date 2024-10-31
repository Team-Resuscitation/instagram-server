package com.resuscitation.instagram.user.entity

import org.springframework.security.core.GrantedAuthority

enum class UserRole : GrantedAuthority {
    ROLE_ADMIN, ROLE_CLIENT;

    override fun getAuthority(): String {
        return name;
    }
}
