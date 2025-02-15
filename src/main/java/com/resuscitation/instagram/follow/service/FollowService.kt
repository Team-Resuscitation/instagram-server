package com.resuscitation.instagram.follow.service

import com.resuscitation.instagram.follow.domain.Follow
import com.resuscitation.instagram.follow.domain.FollowRequest
import com.resuscitation.instagram.follow.repository.FollowRepository
import com.resuscitation.instagram.follow.repository.FollowRequestRepository
import org.springframework.stereotype.Service

@Service
class FollowService(
    val followRequestRepository: FollowRequestRepository,
    private val followRepository: FollowRepository,
) : FollowRequestService, FollowManagementService {
    override fun requestFollow(followerNickname: String, followingNickname: String): Boolean {
        followRequestRepository.save(
            FollowRequest(
                follower = followerNickname,
                following = followingNickname,
            )
        )
        return true
    }

    override fun cancelFollow(followerNickname: String, followingNickname: String): Boolean {
        followRequestRepository.delete(followRequestRepository.findByFollowerAndFollowing(followerNickname, followingNickname))
        return true
    }

    override fun acceptFollowRequest(followerNickname: String, followingNickname: String): Boolean {
        val followRequest = followRequestRepository.findByFollowerAndFollowing(followerNickname, followingNickname)
        followRepository.save(
            Follow(
                follower = followerNickname,
                following = followingNickname,
            )
        )
        followRequestRepository.delete(followRequest)
        return true
    }

    override fun denyFollowRequest(followerNickname: String, followingNickname: String): Boolean {
        followRequestRepository.deleteByFollowerAndFollowing(followerNickname, followingNickname)
        return true
    }

    override fun unfollow(followerNickname: String, followingNickname: String): Boolean {
        followRepository.deleteByFollowerAndFollowing(followerNickname, followingNickname)
        return true
    }
}