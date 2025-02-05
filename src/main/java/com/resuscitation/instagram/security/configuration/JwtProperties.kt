package com.resuscitation.instagram.security.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "instagram.jwt")
data class JwtProperties(
    val secretKey: String,
    val accessExpiration: Long,
    val longLivedAccessExpiration: Long,
)
