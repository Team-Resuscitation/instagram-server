package com.resuscitation.Instagram.user.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "로그인 객체")
class LoginFormDto(
        @Schema(description = "이메일", example = "test@test.com")
        var email: String = "",
        @Schema(description = "비밀번호", example = "password!!!")
        var password: String = ""
)