package com.resuscitation.instagram.follow.repository

import com.resuscitation.instagram.follow.domain.FollowRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FollowRequestRepository : JpaRepository<FollowRequest, Long> {
    fun findByFollowerAndFollowing(follower: String, following: String): FollowRequest
    fun deleteByFollowerAndFollowing(follower: String, following: String)
}