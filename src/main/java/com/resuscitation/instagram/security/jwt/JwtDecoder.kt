package com.resuscitation.instagram.security.jwt

import com.resuscitation.instagram.security.auth.dto.AuthenticatedUserDto
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import org.springframework.security.core.Authentication

interface JwtDecoder {
    fun getAuthentication(user: AuthenticatedUserDto): Authentication
    fun validateToken(jws: Jws<Claims>): Boolean
}
