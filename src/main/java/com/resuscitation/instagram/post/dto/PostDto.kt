package com.resuscitation.instagram.post.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.multipart.MultipartFile
import java.util.Date

@Schema(description = "Posting request object")
data class PostRequestDto(
    val author: String,
    val medias: List<MultipartFile>,
    val content: String,
)

@Schema(description = "Posting response object")
data class PostResponseDto(
    val author: String,
    val medias: List<String>,
    val content: String,
    val timestamp: Date,
    val likes: Int,
    val comments: Int,
)
