package com.resuscitation.instagram.follow.controller

import com.resuscitation.instagram.follow.dto.FollowDto
import com.resuscitation.instagram.follow.service.FollowService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v2/follow")
@Tag(name = "Follow API", description = "API for managing follow requests and relationships")
class FollowController(
    private val followService: FollowService
) {
    @Operation(
        summary = "Send a follow request",
        description = "Allows the authenticated user to send a follow request to another user.",
        responses = [
            ApiResponse(responseCode = "200", description = "Follow request sent successfully"),
            ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            ApiResponse(responseCode = "401", description = "Unauthorized access")
        ]
    )
    @PostMapping("/request")
    fun requestFollow(
        @AuthenticationPrincipal @Parameter(hidden = true) user: OAuth2User,
        @RequestBody followingNickname: String,
    ): ResponseEntity<Boolean> {
        return ResponseEntity.ok(followService.requestFollow(user.name, followingNickname))
    }

    @Operation(
        summary = "Cancel a follow request",
        description = "Allows the authenticated user to cancel a previously sent follow request.",
        responses = [
            ApiResponse(responseCode = "200", description = "Follow request canceled successfully"),
            ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            ApiResponse(responseCode = "401", description = "Unauthorized access"),
            ApiResponse(responseCode = "404", description = "Follow request not found")
        ]
    )
    @PostMapping("/request/cancel")
    fun cancelFollow(
        @AuthenticationPrincipal @Parameter(hidden = true) user: OAuth2User,
        @RequestBody followingNickname: String,
    ): ResponseEntity<Boolean> {
        return ResponseEntity.ok(followService.cancelFollow(user.name, followingNickname))
    }

    @Operation(
        summary = "Accept a follow request",
        description = "Allows the authenticated user to accept a pending follow request.",
        responses = [
            ApiResponse(responseCode = "200", description = "Follow request accepted successfully"),
            ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            ApiResponse(responseCode = "401", description = "Unauthorized access"),
            ApiResponse(responseCode = "404", description = "Follow request not found")
        ]
    )
    @PostMapping("/request/accept")
    fun acceptFollow(
        @AuthenticationPrincipal @Parameter(hidden = true) user: OAuth2User,
        @RequestBody followerNickname: String
    ): ResponseEntity<Boolean> {
        return ResponseEntity.ok(followService.acceptFollowRequest(followerNickname, user.name))
    }

    @Operation(
        summary = "Deny a follow request",
        description = "Allows the authenticated user to deny a pending follow request.",
        responses = [
            ApiResponse(responseCode = "200", description = "Follow request denied successfully"),
            ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            ApiResponse(responseCode = "401", description = "Unauthorized access"),
            ApiResponse(responseCode = "404", description = "Follow request not found")
        ]
    )
    @PostMapping("/request/deny")
    fun denyFollow(
        @AuthenticationPrincipal @Parameter(hidden = true) user: OAuth2User,
        @RequestBody followerNickname: String
    ): ResponseEntity<Boolean> {
        return ResponseEntity.ok(followService.denyFollowRequest(followerNickname, user.name))
    }

    @Operation(
        summary = "Get followers list",
        description = "Retrieves a list of users who are following the specified user.",
        responses = [
            ApiResponse(responseCode = "200", description = "Followers list retrieved successfully"),
            ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            ApiResponse(responseCode = "404", description = "User not found")
        ]
    )
    @GetMapping("/follower")
    fun getFollowers(nickname: String): ResponseEntity<List<FollowDto>> {
        return ResponseEntity.ok(followService.getFollowers(nickname))
    }

    @Operation(
        summary = "Get following list",
        description = "Retrieves a list of users whom the specified user is following.",
        responses = [
            ApiResponse(responseCode = "200", description = "Following list retrieved successfully"),
            ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            ApiResponse(responseCode = "404", description = "User not found")
        ]
    )
    @GetMapping("/following")
    fun getFollowings(nickname: String): ResponseEntity<List<FollowDto>> {
        return ResponseEntity.ok(followService.getFollowings(nickname))
    }
}
