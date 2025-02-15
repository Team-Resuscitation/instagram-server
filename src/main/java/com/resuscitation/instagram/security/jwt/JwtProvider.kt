package com.resuscitation.instagram.security.jwt

import com.resuscitation.instagram.security.auth.domain.User
import com.resuscitation.instagram.security.auth.domain.UserRole
import com.resuscitation.instagram.security.auth.dto.AuthenticatedUserDto
import com.resuscitation.instagram.security.auth.repository.UserRepository
import com.resuscitation.instagram.security.configuration.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.Jwts.SIG.HS256
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.MacAlgorithm
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*
import javax.crypto.SecretKey

@Component
@EnableConfigurationProperties(JwtProperties::class)
class JwtProvider(
    private val jwtProperties: JwtProperties,
    private val redisTemplate: RedisTemplate<Long, Any>,
    private val userRepository: UserRepository
) : JwtEncoder, JwtDecoder, JwtExtractor, JwtService {
    private val hmacSecretKey: SecretKey = Keys.hmacShaKeyFor(jwtProperties.secretKey.toByteArray())
    private val algorithm: MacAlgorithm = HS256

    override fun generateToken(user: User, expiration: Long): String {
        val currentTime = Instant.now()
        val jwt = Jwts.builder()
            .subject(user.nickname)
            .claim("role", UserRole.ROLE_BASIC.name)
            .claim("audience", "instagram")
            .issuer("team.resuscitation")
            .issuedAt(Date.from(currentTime))
            .expiration(Date.from(currentTime.plusMillis(jwtProperties.accessExpiration)))
            .signWith(hmacSecretKey, algorithm)
            .compact()
        redisTemplate.opsForValue().set(user.uuid, jwt)
        return jwt
    }

    override fun extractToken(token: String): AuthenticatedUserDto {
        val jws = Jwts.parser()
            .verifyWith(hmacSecretKey)
            .build()
            .parseSignedClaims(token)

        validateToken(jws)

        val claims = jws.payload
        return AuthenticatedUserDto(
            nickname = claims.subject,
            role = claims["role"] as UserRole,
            audience = claims["audience"] as String,
            issuer = claims.issuer,
            issuedAt = claims.issuedAt,
            expiresAt = claims.expiration,
        )
    }

    override fun getAuthentication(user: AuthenticatedUserDto): Authentication {
        val authorities = listOf(SimpleGrantedAuthority(user.role.name))
        return UsernamePasswordAuthenticationToken(user.nickname, null, authorities)
    }

    override fun validateToken(jws: Jws<Claims>): Boolean {
        val claims = jws.payload
        if (jws.header.algorithm != algorithm.toString()) {
            throw JwtException("Invalid JWT algorithm")
        }
        if (claims["role"] as UserRole != UserRole.ROLE_BASIC) {
            throw JwtException("Invalid user role")
        }
        if (claims["audience"] as String != "instagram") {
            throw JwtException("wrong audience")
        }
        if (claims.issuer != "team.resuscitation") {
            throw JwtException("wrong issuer")
        }
        if (claims.issuedAt.after(Date())) {
            throw JwtException("Token has issued in future")
        }
        if (claims.expiration.before(Date())) {
            throw JwtException("Token has expired")
        }

        return true
    }


    override fun invalidateToken(nickname: String): Boolean {
        redisTemplate.delete(userRepository.findByNickname(nickname).uuid)
        return true
    }
}