package com.resuscitation.instagram.follow.service

import com.resuscitation.instagram.follow.dto.FollowDto

interface FollowInfoService {
    fun getFollowers(nickname: String): List<FollowDto>

    fun getFollowings(nickname: String): List<FollowDto>
}
