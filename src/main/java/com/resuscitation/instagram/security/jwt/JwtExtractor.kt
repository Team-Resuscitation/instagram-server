package com.resuscitation.instagram.security.jwt

interface JwtExtractor {
    fun extractToken(token: String): com.resuscitation.instagram.security.auth.dto.AuthenticatedUserDto
}