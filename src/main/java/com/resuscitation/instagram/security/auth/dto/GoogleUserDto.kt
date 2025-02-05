package com.resuscitation.instagram.security.auth.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Google OAuth2 user information")
data class GoogleUserDto(
    @Schema(description = "OAuth2 provider (e.g., Google)", example = "google")
    val provider: String,

    @Schema(description = "Provider-specific unique user ID", example = "1234567890")
    val providerId: String,

    @Schema(description = "User's full name", example = "John Doe")
    val name: String,

    @Schema(description = "User's email address", example = "johndoe@gmail.com")
    val email: String,
)
