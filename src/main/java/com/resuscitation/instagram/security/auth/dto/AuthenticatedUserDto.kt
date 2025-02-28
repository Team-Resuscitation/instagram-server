package com.resuscitation.instagram.security.auth.dto

import com.resuscitation.instagram.security.auth.domain.UserRole
import io.swagger.v3.oas.annotations.media.Schema
import java.util.Date

@Schema(description = "Authenticated user object")
data class AuthenticatedUserDto(
    @Schema(description = "Nickname", example = "minimin.e")
    val nickname: String,
    @Schema(description = "Administrator rights", example = "USER_BASIC")
    val role: UserRole,
    @Schema(description = "Audience", example = "Instagram")
    val audience: String,
    @Schema(description = "Issuer", example = "Team.resuscitation")
    val issuer: String,
    @Schema(description = "Issue Time", example = "2025-01-01T10:15:30Z")
    val issuedAt: Date,
    @Schema(description = "Expire Time", example = "2025-01-08T10:15:30Z")
    val expiresAt: Date,
)
