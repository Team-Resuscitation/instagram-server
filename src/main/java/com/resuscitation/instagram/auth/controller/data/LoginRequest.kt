package com.resuscitation.instagram.auth.controller.data

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Login request object")
data class LoginRequest(
    @Schema(description = "유저 고유 키(닉네임, 이메일, 전화번호 중 하나)", example = "minimin.e")
    val key: String,
    @Schema(description = "유저 비밀번호", example = "securePassword123")
    val password: String,
)
