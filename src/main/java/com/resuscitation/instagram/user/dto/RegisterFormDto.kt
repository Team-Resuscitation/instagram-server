package com.resuscitation.instagram.user.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Pattern

/**
 * 회원가입 폼
 */
@Schema(description = "회원가입 Dto")
class RegisterFormDto(
        @Schema(description = "이메일", example = "test@test.com")
        var email: String = "",
        @Schema(description = "비밀번호", example = "password!!!")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d!@#\$%^&*()\\-_=+{};:,<.>/?]).{8,}\$\n")
        var password: String = "",
        @Schema(description = "닉네임", example = "tester_nickname")
        var nickname: String = "",
        @Schema(description = "유저 이름", example = "tester_name")
        var name: String = "",
        @Schema(description = "설명", example = "테스트 유저 입니다.")
        var introduce: String = "",
){
        override fun toString(): String {
                return "RegisterFormDto(email='$email', password='$password', nickname='$nickname', name='$name', introduce='$introduce')"
        }

}
