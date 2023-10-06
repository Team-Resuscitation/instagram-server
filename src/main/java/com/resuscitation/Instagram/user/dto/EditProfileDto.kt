package com.resuscitation.Instagram.user.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.annotation.Nonnull

@Schema(description = "회원 정보 수정 객체")
class EditProfileDto(
        @Nonnull
        @Schema(description = "닉네임")
        var nickname: String = "",
        @Schema(description = "이름")
        var name: String = "",
        @Schema(description = "소개글")
        var introduce: String = "",
        @Schema(description = "프로필 이미지")
        var profileImage: String = "",
        
)