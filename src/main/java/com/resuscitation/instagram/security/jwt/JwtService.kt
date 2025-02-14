package com.resuscitation.instagram.security.jwt

interface JwtService {
    fun invalidateToken(nickname: String): Boolean
}