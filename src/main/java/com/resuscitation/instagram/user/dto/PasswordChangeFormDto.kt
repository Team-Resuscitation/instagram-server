package com.resuscitation.instagram.user.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.annotation.Nonnull
import jakarta.validation.constraints.Pattern

@Schema(description = "Password Changing Form")
class PasswordChangeFormDto(
        @Nonnull
        @Schema(description = "현재 비밀번호", example = "password!!!")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d!@#\$%^&*()\\-_=+{};:,<.>/?]).{8,}\$\n")
        var currentPassword: String = "",
        @Nonnull
        @Schema(description = "새로운 비밀번호", example = "password!!!")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d!@#\$%^&*()\\-_=+{};:,<.>/?]).{8,}\$\n")
        var newPassword: String = "",
        @Nonnull
        @Schema(description = "비밀번호 재입력", example = "password!!!")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d!@#\$%^&*()\\-_=+{};:,<.>/?]).{8,}\$\n")
        var rePassword: String = ""
)
