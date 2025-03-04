package com.resuscitation.instagram.user.controller

import com.resuscitation.instagram.common.domain.AuthenticatedUser
import com.resuscitation.instagram.config.SwaggerConfig.Companion.BEARER_AUTH
import com.resuscitation.instagram.user.domain.Following
import com.resuscitation.instagram.user.service.FollowingService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "Follow API", description = "팔로우 관련 API")
@RequestMapping("/api/v1/users/{userId}")
@SecurityRequirement(name = BEARER_AUTH)
class UserFollowController(
    private val followingService: FollowingService
) {
    @GetMapping("/followers")
    @Operation(
        summary = "팔로워 목록 조회",
        description = "대상 유저의 팔로워 목록을 조회합니다.",
    )
    fun getFollowers(
        @Parameter(description = "대상 유저 ID") @PathVariable userId: Long,
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser?,
        @ParameterObject pageable: Pageable,
    ): ResponseEntity<Page<Following>> {
        return ResponseEntity.ok(
            followingService.getFollowers(
                userId = userId, isMe = authenticatedUser?.userId == userId, pageable = pageable
            )
        )
    }

    @GetMapping("/followings")
    @Operation(
        summary = "팔로잉 목록 조회",
        description = """
대상 유저의 팔로잉 목록을 조회합니다.
유저 본인인 경우, 팔로우 요청 목록도 함께 조회합니다.
        """,
    )
    fun getFollowings(
        @Parameter(description = "대상 유저 ID") @PathVariable userId: Long,
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser?,
        @ParameterObject pageable: Pageable,
    ): ResponseEntity<Page<Following>> {
        return ResponseEntity.ok(
            followingService.getFollowings(
                userId = userId,
                isMe = authenticatedUser?.userId == userId,
                pageable = pageable,
            )
        )
    }

    @PostMapping("/follow")
    @Operation(summary = "팔로우 요청 하기")
    fun follow(
        @Parameter(description = "대상 유저 ID") @PathVariable userId: Long,
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
    ): ResponseEntity<Following.Status> {
        val result = followingService.follow(
            userId = authenticatedUser.userId,
            followingId = userId,
        )
        // 팔로워 숫자 동기화는 비동기로 처리
        followingService.syncFollowerCounts(userId)
        return ResponseEntity.ok(
            result
        )
    }

    @DeleteMapping("/unfollow")
    @Operation(summary = "언팔로우 하기")
    fun unfollow(
        @Parameter(description = "대상 유저 ID") @PathVariable userId: Long,
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
    ): ResponseEntity<Unit> {
        followingService.unfollow(
            userId = authenticatedUser.userId,
            followingId = userId,
        )
        // 팔로워 숫자 동기화는 비동기로 처리
        followingService.syncFollowerCounts(userId)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/follow/{followId}/allow")
    @PreAuthorize("#authenticatedUser.userId == #userId")
    @Operation(summary = "팔로우 허용하기 (본인용)")
    fun allowFollow(
        @Parameter(description = "본인 ID") @PathVariable userId: Long,
        @Parameter(description = "팔로워 ID") @PathVariable followId: Long,
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
    ): ResponseEntity<Unit> {
        followingService.allowFollowRequest(
            userId = authenticatedUser.userId,
            followingId = followId,
        )
        // 팔로워 숫자 동기화는 비동기로 처리
        followingService.syncFollowerCounts(userId)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/follow/{followId}")
    @PreAuthorize("#authenticatedUser.userId == #userId")
    @Operation(summary = "팔로우 끊기 (본인용)")
    fun blockFollow(
        @Parameter(description = "본인 ID") @PathVariable userId: Long,
        @Parameter(description = "팔로워 ID") @PathVariable followId: Long,
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
    ): ResponseEntity<Unit> {
        followingService.unfollow(
            userId = authenticatedUser.userId,
            followingId = followId,
        )
        // 팔로워 숫자 동기화는 비동기로 처리
        followingService.syncFollowerCounts(userId)
        return ResponseEntity.noContent().build()
    }
}
