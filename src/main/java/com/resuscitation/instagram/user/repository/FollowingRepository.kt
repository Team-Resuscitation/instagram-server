package com.resuscitation.instagram.user.repository

import com.resuscitation.instagram.user.domain.Following
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface FollowingRepository : JpaRepository<Following, Following.FollowingId> {
    /**
     * 팔로잉 수를 동기화합니다.
     * @param userId 사용자 식별자
     */
    @Modifying
    @Query(
        """
        UPDATE User u
        SET u.followingsCount = (
            SELECT COUNT(*) 
            FROM Following f
            WHERE f.id.followeeId = :userId
            AND f.status = Following.Status.ACCEPTED
        )
    """
    )
    fun syncFolloweeCounts(userId: Long)

    /**
     * 팔로워 수를 동기화합니다.
     * @param userId 사용자 식별자
     */
    @Modifying
    @Query(
        """
        UPDATE User u
        SET u.followersCount = (
            SELECT COUNT(*) 
            FROM Following f
            WHERE f.id.followerId = :userId
            AND f.status = Following.Status.ACCEPTED
        )
    """
    )
    fun syncFollowerCounts(userId: Long)

    /**
     * 팔로잉 목록을 조회합니다.
     * @param followerId 팔로워 식별자
     * @param pageable 페이지 정보
     * @return 팔로잉 목록
     */
    @Query(
        """
        SELECT f FROM Following f 
        WHERE f.id.followeeId = :userId
        AND (:status IS NULL OR f.status = :status)
    """
    )
    fun findByIdFollowerIdAndStatus(
        followerId: Long,
        status: Following.Status?,
        pageable: Pageable
    ): Page<Following>

    /**
     * 팔로워 목록을 조회합니다.
     * @param followingId 팔로잉 식별자
     * @param pageable 페이지 정보
     * @return 팔로워 목록
     */
    @Query(
        """
        SELECT f FROM Following f 
        WHERE f.id.followerId = :userId
        AND (:status IS NULL OR f.status = :status)
    """
    )
    fun findByIdFollowingIdAndStatus(
        followingId: Long,
        status: Following.Status?,
        pageable: Pageable
    ): Page<Following>
}
