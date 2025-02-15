package com.resuscitation.instagram.follow.repository

import com.resuscitation.instagram.follow.domain.Follow
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FollowRepository: JpaRepository<Follow, Long> {
    fun deleteByFollowerAndFollowing(follower: String, following: String)
}