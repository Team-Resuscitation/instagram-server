package com.resuscitation.instagram.security.auth.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Pattern

@Schema(description = "Password changing form object")
data class PasswordChangeRequestDto(
    @Schema(description = "User nickname whose password is be updated")
    val nickname: String,
    @Schema(description = "current password", example = "password!!!")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d!@#\$%^&*()\\-_=+{};:,<.>/?]).{8,}\$\n")
    val currentPassword: String = "",
    @Schema(description = "New password", example = "password!!!")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d!@#\$%^&*()\\-_=+{};:,<.>/?]).{8,}\$\n")
    val newPassword: String = "",
    @Schema(description = "Confirmation of the new password", example = "password!!!")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d!@#\$%^&*()\\-_=+{};:,<.>/?]).{8,}\$\n")
    val confirmPassword: String = "",
)

@Schema(description = "Password changing form object")
data class PasswordChangeResponseDto(
    @Schema(description = "Success updating password", example = "true")
    val success: Boolean,
    @Schema(description = "Error message", example = "Wrong password")
    val message: String?,
)
