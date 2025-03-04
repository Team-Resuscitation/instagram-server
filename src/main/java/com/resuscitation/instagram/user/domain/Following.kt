package com.resuscitation.instagram.user.domain

import jakarta.persistence.Embeddable
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Entity
class Following(
    @EmbeddedId
    val id: FollowingId,

    @Enumerated(value = EnumType.STRING)
    var status: Status = Status.REQUESTED
) {

    @Embeddable
    data class FollowingId(
        /**
         * Follower
         * 하꼬 유저
         */
        val followerId: Long,
        /**
         *  Following target
         *  인플루언서
         */
        val followeeId: Long
    )

    enum class Status {
        REQUESTED,
        ACCEPTED,
    }
}
