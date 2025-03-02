package com.resuscitation.instagram.auth.controller.data

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "User registration request object")
data class RegisterRequest(
    @Schema(description = "Nickname for the user", example = "user123")
    val nickname: String,
    @Schema(
        description =
            "Password must be at least 8 characters long and include uppercase, lowercase letters, " +
                    "numbers and special characters.",
        example = "Secure@1234",
    )
    val password: String,
    @Schema(description = "User's phone number", example = "010-1234-5678")
    val phoneNumber: String,
    @Schema(description = "User's email address", example = "user@example.com")
    val email: String,
)
