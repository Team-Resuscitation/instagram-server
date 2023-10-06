package com.resuscitation.Instagram.user.dto

import com.resuscitation.Instagram.user.entity.UserEntity
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.ElementCollection

class ProfileDto(
        // TODO: dto 변경후 불필요한 정보 삭제 해야함
        var userEntity: UserEntity,

        //TODO : article repository에 맞춰서 변경
        @ElementCollection
        @Schema(description = "게시물 리스트", example = "none")
        var postArray: ArrayList<String>,
) {
    override fun toString(): String {
        return "ProfileDto (userEntity = $userEntity, postArray= $postArray)"
    }
}