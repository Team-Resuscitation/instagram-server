package com.resuscitation.instagram.auth.controller.data

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Login response object")
data class LoginResponse(
    @Schema(description = "JWT access token", example = "eyJhbGciOiJIUzUxMiIsInR...")
    val accessToken: String,
)
