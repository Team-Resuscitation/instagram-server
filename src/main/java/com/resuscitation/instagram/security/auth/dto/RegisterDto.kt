package com.resuscitation.instagram.security.auth.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "User registration request object")
data class RegisterRequestDto(
    @Schema(description = "Nickname for the user", example = "user123")
    val nickname: String = "",
    @Schema(
        description =
            "Password must be at least 8 characters long and include uppercase, lowercase letters, " +
                "numbers and special characters.",
        example = "Secure@1234",
    )
    val password: String = "",
    @Schema(description = "User's phone number", example = "010-1234-5678")
    val phoneNumber: String = "",
    @Schema(description = "User's email address", example = "user@example.com")
    val email: String = "",
)

@Schema(description = "User registration response object")
data class RegisterResponseDto(
    @Schema(description = "Success updating password", example = "true")
    val success: Boolean,
    @Schema(description = "Error message", example = "Wrong password")
    val message: String?,
)
