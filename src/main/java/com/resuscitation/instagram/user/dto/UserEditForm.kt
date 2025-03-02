package com.resuscitation.instagram.user.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    description = """회원 정보 수정 객체
    수정 하지 않을 객체는 null로 보내주세요)
    """,
)
class UserEditForm(
    @Schema(description = "닉네임")
    var nickname: String? = null,
    @Schema(description = "이름")
    var name: String? = null,
    @Schema(description = "소개글")
    var introduce: String? = null,
)
