package com.resuscitation.instagram.follow.service

interface FollowManagementService {
    fun acceptFollowRequest(
        followerNickname: String,
        followingNickname: String,
    ): Boolean

    fun denyFollowRequest(
        followerNickname: String,
        followingNickname: String,
    ): Boolean

    fun unfollow(
        followerNickname: String,
        followingNickname: String,
    ): Boolean
}
