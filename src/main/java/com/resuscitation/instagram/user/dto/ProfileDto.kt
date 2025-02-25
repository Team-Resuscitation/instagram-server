package com.resuscitation.instagram.user.dto

import com.resuscitation.instagram.post.dto.PostResponseDto
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "User profile response object")
data class ProfileDto(
    @Schema(description = "The nickname of the user", example = "user123")
    val nickname: String,
    @Schema(description = "The name of the user", example = "John Doe")
    val name: String,
    @Schema(description = "Introduction or bio of the user", example = "Hello! I love coding and sharing knowledge.")
    val introduce: String,
    @Schema(description = "URL to the user's profile image", example = "https://example.com/images/user123.jpg")
    val profileImage: String,
    @Schema(description = "User feed")
    val feed: List<PostResponseDto>,
)
