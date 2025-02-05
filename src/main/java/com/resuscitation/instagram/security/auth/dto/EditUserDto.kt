package com.resuscitation.instagram.security.auth.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "User account edit request object")
data class EditUserRequestDto(
    @Schema(description = "User's nickname to be updated", example = "minimin.e")
    val nickname: String,
    @Schema(description = "User's new email address", example = "user@example.com")
    val email: String? = null,
    @Schema(description = "User's new phone number", example = "+1234567890")
    val phoneNumber: String? = null,
)

@Schema(description = "User account edit response object")
data class EditUserResponseDto(
    @Schema(description = "Success updating password", example = "true")
    val success: Boolean,
    @Schema(description = "Error message", example = "Wrong password")
    val message: String?,
)
