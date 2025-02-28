package com.resuscitation.instagram.security.auth.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Login request object")
data class LoginRequestDto(
    @Schema(description = "User nickname", example = "minimin.e", nullable = true)
    val nickname: String? = null,
    @Schema(description = "User password", example = "securePassword123")
    val password: String = "",
    @Schema(description = "User phone number", example = "+1234567890", nullable = true)
    val phoneNumber: String? = null,
    @Schema(description = "User email address", example = "user@example.com", nullable = true)
    val email: String? = null,
)

@Schema(description = "Login response object")
data class LoginResponseDto(
    @Schema(description = "JWT access token", example = "eyJhbGciOiJIUzUxMiIsInR...")
    val accessToken: String,
)
