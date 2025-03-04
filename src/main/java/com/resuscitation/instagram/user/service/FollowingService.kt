package com.resuscitation.instagram.user.service

import com.resuscitation.instagram.user.domain.Following
import com.resuscitation.instagram.user.domain.User
import com.resuscitation.instagram.user.repository.FollowingRepository
import com.resuscitation.instagram.user.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FollowingService(
    private val followingRepository: FollowingRepository,
    private val userRepository: UserRepository,
) {
    @Transactional(readOnly = true)
    fun getFollowers(
        userId: Long,
        isMe: Boolean,
        pageable: Pageable
    ): Page<Following> {
        return followingRepository.findByIdFollowerIdAndStatus(
            followerId = userId,
            status = if (isMe) null else Following.Status.ACCEPTED,
            pageable = pageable,
        )
    }

    @Transactional(readOnly = true)
    fun getFollowings(
        userId: Long,
        isMe: Boolean,
        pageable: Pageable
    ): Page<Following> {
        return followingRepository.findByIdFollowingIdAndStatus(
            followingId = userId,
            status = if (isMe) null else Following.Status.ACCEPTED,
            pageable = pageable,
        )
    }

    @Transactional
    fun follow(
        userId: Long,
        followingId: Long,
    ): Following.Status {
        val targetUser: User = userRepository.findByIdOrNull(userId) ?: throw IllegalStateException()

        val result = followingRepository.save(
            Following(
                Following.FollowingId(userId, followingId),
                if (targetUser.isPrivate) Following.Status.REQUESTED else Following.Status.ACCEPTED,
            )
        )
        return result.status
    }

    @Transactional
    fun allowFollowRequest(
        userId: Long,
        followingId: Long,
    ) {
        val following = followingRepository.findByIdOrNull(
            Following.FollowingId(followingId, userId)
        ) ?: throw IllegalStateException()

        following.status = Following.Status.ACCEPTED
        followingRepository.save(following)
    }

    @Transactional
    fun unfollow(userId: Long, followingId: Long) {
        followingRepository.deleteById(
            Following.FollowingId(userId, followingId)
        )
    }

    @Async
    @Transactional
    fun syncFollowerCounts(userId: Long) {
        followingRepository.syncFollowerCounts(userId)
        followingRepository.syncFolloweeCounts(userId)
    }
}
