package com.resuscitation.instagram.user.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "User profile update object")
data class UpdateProfileDto(
    @Schema(description = "Nickname", example = "minimin.e")
    var nickname: String = "",
    @Schema(description = "Name", example = "Jane Doe")
    var name: String = "",
    @Schema(description = "User Introduction", example = "Hi I'm, Jane")
    var introduce: String = "",
    @Schema(description = "Profile image", example = "https://example.com/images/user123.jpg")
    var profileImage: String = "",
)