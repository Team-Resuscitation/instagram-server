package com.resuscitation.instagram.common.domain

import com.resuscitation.instagram.user.domain.UserRole
import java.time.Instant

/**
 * JWT Payload에 저장되는 사용자 정보
 */
data class AuthenticatedUser(
    /** User ID */
    val userId: Long,
    /** Role */
    val roles: Set<UserRole>,
    /** 발행자 */
    val issuer: String,
    /** 발행 시간 */
    val issuedAt: Instant,
    /** 만료 시간 */
    val expiresAt: Instant,
)
