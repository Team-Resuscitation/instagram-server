package com.resuscitation.instagram.post.controller

import com.resuscitation.instagram.common.domain.AuthenticatedUser
import com.resuscitation.instagram.config.SwaggerConfig.Companion.BEARER_AUTH
import com.resuscitation.instagram.config.SwaggerConfig.Companion.POST_API
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = POST_API, description = "Post APIs")
@RestController
@RequestMapping("/api/v2/posts/{postId}/like")
class PostLikeController {
    @GetMapping
    @Operation(summary = "게시글 좋아요 유저 목록 조회", description = "게시글 좋아요를 누른 유저 목록을 조회합니다.")
    fun getPostLikeUserList(
        @PathVariable postId: Long,
    ) {
        // 게시글 좋아요 유저 목록 조회
        TODO("Not yet implemented")
    }

    @PostMapping
    @Operation(summary = "게시글 좋아요", description = "게시글에 좋아요를 누릅니다.")
    @SecurityRequirement(name = BEARER_AUTH)
    fun postLike(
        @PathVariable postId: Long,
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
    ) {
        // 게시글 좋아요
        TODO("Not yet implemented")
    }

    @DeleteMapping
    @Operation(summary = "게시글 좋아요 취소", description = "게시글에 누른 좋아요를 취소합니다.")
    @SecurityRequirement(name = BEARER_AUTH)
    fun deleteLike(
        @PathVariable postId: Long,
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
    ) {
        // 게시글 좋아요 취소
        TODO("Not yet implemented")
    }
}
