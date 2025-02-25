package com.resuscitation.instagram.follow.service

interface FollowRequestService {
    fun requestFollow(
        followerNickname: String,
        followingNickname: String,
    ): Boolean

    fun cancelFollow(
        followerNickname: String,
        followingNickname: String,
    ): Boolean
}
