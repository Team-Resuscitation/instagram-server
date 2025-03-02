package com.resuscitation.instagram.auth

import com.resuscitation.instagram.auth.properties.JwtProperties
import com.resuscitation.instagram.common.domain.AuthenticatedUser
import com.resuscitation.instagram.user.domain.User
import com.resuscitation.instagram.user.domain.UserRole
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.Jwts.SIG.HS256
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.MacAlgorithm
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.Date
import javax.crypto.SecretKey

@Component
@EnableConfigurationProperties(JwtProperties::class)
class TokenProvider(
    private val jwtProperties: JwtProperties,
) : JwtEncoder, JwtDecoder {
    private val hmacSecretKey: SecretKey = Keys.hmacShaKeyFor(jwtProperties.secretKey.toByteArray())
    private val algorithm: MacAlgorithm = HS256

    override fun encode(user: User): String {
        return Jwts.builder()
            .subject(user.id.toString())
            .claim("roles", user.roles)
            .issuer(jwtProperties.issuer)
            .issuedAt(Date.from(Instant.now()))
            .expiration(Date.from(Instant.now().plusMillis(jwtProperties.expiration)))
            .signWith(hmacSecretKey, algorithm)
            .compact()
    }

    override fun decode(token: String): AuthenticatedUser {
        val jws =
            Jwts.parser()
                .verifyWith(hmacSecretKey)
                .build()
                .parseSignedClaims(token)

        val claims = jws.payload

        return AuthenticatedUser(
            userId = claims.subject.toLong(),
            roles = claims["roles"] as? Set<UserRole> ?: emptySet(),
            issuer = claims.issuer,
            issuedAt = claims.issuedAt.toInstant(),
            expiresAt = claims.expiration.toInstant(),
        )
    }
}
