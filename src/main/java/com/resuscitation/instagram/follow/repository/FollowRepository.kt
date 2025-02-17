package com.resuscitation.instagram.follow.repository

import com.resuscitation.instagram.follow.domain.Follow
import com.resuscitation.instagram.follow.dto.FollowDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface FollowRepository: JpaRepository<Follow, Long> {
    fun deleteByFollowerAndFollowing(follower: String, following: String)

    @Query("SELECT FollowDto(p.profileImage, f.follower, f.following) " +
            "FROM Follow f JOIN Profile p ON f.follower = p.nickname " +
            "WHERE f.follower = :nickname")
    fun findFollowsByNickname(@Param("nickname") nickname: String): List<FollowDto>

    @Query("SELECT FollowDto(p.profileImage, f.follower, f.following) " +
            "FROM Follow f JOIN Profile p  ON f.following = p.nickname " +
            "WHERE f.following = :nickname")
    fun findFollowingsByNickname(nickname: String): List<FollowDto>
}