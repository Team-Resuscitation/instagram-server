package com.resuscitation.instagram.user.service

import com.resuscitation.instagram.user.dto.ProfileDto
import com.resuscitation.instagram.user.dto.UpdateProfileDto

interface ProfileService {
    fun showProfile(nickname: String): ProfileDto

    fun updateProfile(
        nickname: String,
        updateProfileDto: UpdateProfileDto,
    ): Boolean
}
