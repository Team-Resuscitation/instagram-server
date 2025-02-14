package com.resuscitation.instagram.security.jwt

import com.resuscitation.instagram.security.auth.domain.User

interface JwtEncoder {
    fun generateToken(user: User, expiration: Long): String
}
