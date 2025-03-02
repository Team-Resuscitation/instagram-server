package com.resuscitation.instagram.post.controller

import com.resuscitation.instagram.common.domain.AuthenticatedUser
import com.resuscitation.instagram.config.SwaggerConfig.Companion.BEARER_AUTH
import com.resuscitation.instagram.config.SwaggerConfig.Companion.POST_API
import com.resuscitation.instagram.post.dto.PostRequestDto
import com.resuscitation.instagram.post.dto.PostResponseDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v2/posts")
@Tag(name = POST_API)
class PostController {
    @PostMapping
    @Operation(summary = "게시글 작성 API")
    @SecurityRequirement(name = BEARER_AUTH)
    fun uploadPost(
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
        @Valid @RequestBody postRequestDto: PostRequestDto,
    ): ResponseEntity<Boolean> {
        TODO("Not yet implemented")
    }

    @GetMapping("/{postId}")
    @Operation(summary = "게시글 조회 API")
    fun readPost(
        @PathVariable postId: String,
    ): ResponseEntity<PostResponseDto> {
        TODO("Not yet implemented")
    }

    @PatchMapping("/{postId}")
    @Operation(summary = "게시글 수정 API")
    @SecurityRequirement(name = BEARER_AUTH)
    fun updatePost(
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
        @PathVariable postId: String,
        @Valid @RequestBody postRequestDto: PostRequestDto,
    ): ResponseEntity<Boolean> {
        TODO("Not yet implemented")
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "게시글 삭제 API")
    @SecurityRequirement(name = BEARER_AUTH)
    fun deletePost(
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
        @PathVariable postId: String,
    ): ResponseEntity<Boolean> {
        TODO("Not yet implemented")
    }
}
