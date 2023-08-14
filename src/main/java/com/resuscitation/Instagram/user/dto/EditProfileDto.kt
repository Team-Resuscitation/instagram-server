package com.resuscitation.Instagram.user.dto

import jakarta.annotation.Nonnull

class EditProfileDto (
        @Nonnull
        var nickname: String = "",
        var name: String = "",
        var introduce: String = ""
)