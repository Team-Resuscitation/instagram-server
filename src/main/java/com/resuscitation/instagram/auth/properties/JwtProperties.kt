package com.resuscitation.instagram.auth.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "instagram.jwt")
data class JwtProperties(
    /** JWT secret key */
    val secretKey: String,
    /** 발행자 */
    val issuer: String,
    /** 액세스 토큰 만료 시간 */
    val expiration: Long = 30 * 60 * 1000, // 30 minutes
)
