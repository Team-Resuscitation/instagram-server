package com.resuscitation.instagram.post.controller

import com.resuscitation.instagram.post.dto.PostRequestDto
import com.resuscitation.instagram.post.dto.PostResponseDto
import com.resuscitation.instagram.post.service.PostServiceImpl
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v2/post")
@Tag(name = "Post", description = "Post Controller APIs")
class PostController(
    private val postService: PostServiceImpl,
) {
    @Operation(
        summary = "Post uploading api",
        description = "Uploads a new post.",
        responses = [
            ApiResponse(responseCode = "200", description = "Post uploaded successfully"),
        ]
    )
    @GetMapping("/upload")
    fun uploadPost(
        @AuthenticationPrincipal @Parameter(hidden = true) user: OAuth2User,
        @Valid @RequestBody postRequestDto: PostRequestDto,
    ): ResponseEntity<Boolean> {
        return ResponseEntity.ok(postService.uploadPost(postRequestDto))
    }

    @Operation(
        summary = "Post reading api",
        description = "Fetches the details of a specific post by its ID.",
        responses = [
            ApiResponse(responseCode = "200", description = "Post retrieved successfully"),
        ]
    )
    @GetMapping("/{postId}")
    fun readPost(
        @PathVariable postId: String,
    ): ResponseEntity<PostResponseDto> {
        return ResponseEntity.ok(postService.readPost(postId))
    }

    @Operation(
        summary = "Post updating api",
        description = "Updates an existing post using its ID.",
        responses = [
            ApiResponse(responseCode = "200", description = "Post updated successfully"),
            ApiResponse(responseCode = "400", description = "Bad request"),
            ApiResponse(responseCode = "404", description = "Post not found")
        ]
    )
    @PatchMapping("/update/{postId}")
    fun updatePost(
        @AuthenticationPrincipal @Parameter(hidden = true) user: OAuth2User,
        @PathVariable postId: String,
        @Valid @RequestBody postRequestDto: PostRequestDto,
    ): ResponseEntity<Boolean> {
        return ResponseEntity.ok(postService.updatePost(postId, postRequestDto))
    }

    @Operation(
        summary = "Post deleting api",
        description = "Deletes a specific post by its ID.",
        responses = [
            ApiResponse(responseCode = "200", description = "Post deleted successfully"),
            ApiResponse(responseCode = "404", description = "Post not found")
        ]
    )
    @DeleteMapping("/delete/{postId}")
    fun deletePost(
        @AuthenticationPrincipal @Parameter(hidden = true) user: OAuth2User,
        @PathVariable postId: String,
    ): ResponseEntity<Boolean> {
        return ResponseEntity.ok(postService.deletePost(postId))
    }

    @Operation(
        summary = "View user's timeline api",
        description = "Fetches posts from the user's timeline.",
        responses = [
            ApiResponse(responseCode = "200", description = "Timeline retrieved successfully")
        ]
    )
    @GetMapping("/timeline")
    fun timeline(
        @AuthenticationPrincipal @Parameter(hidden = true) user: OAuth2User,
    ): ResponseEntity<List<PostResponseDto>> {
        return ResponseEntity.ok(postService.timeline(user.name))
    }
}