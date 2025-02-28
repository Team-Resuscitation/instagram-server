package com.resuscitation.instagram.user.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Pattern

@Schema(description = "로그인 객체")
class LoginFormDto(
    @Schema(description = "이메일", example = "test@test.com")
    var email: String = "",
    @Schema(description = "비밀번호", example = "password!!!")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d!@#\$%^&*()\\-_=+{};:,<.>/?]).{8,}\$\n")
    var password: String = "",
)
