package com.resuscitation.instagram.security.auth.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "User account deletion request object")
data class DeleteUserRequestDto(
    @Schema(description = "Nickname", example = "minimin.e")
    val nickname: String,
    @Schema(description = "Password")
    val password: String,
)

data class DeleteUserResponseDto(
    @Schema(description = "Success updating password", example = "true")
    val success: Boolean,
    @Schema(description = "Error message", example = "Wrong password")
    val message: String?,
)