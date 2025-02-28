package com.resuscitation.instagram.post.dto

import com.resuscitation.instagram.security.auth.domain.User
import io.swagger.v3.oas.annotations.media.Schema

class ArticleDto(
    @Schema(description = "게시물 번호", example = "1")
    var idx: Long,
    @Schema(description = "게시물 작성자", example = "tester_nickname")
    var author: User,
    @Schema(description = "사진", example = "test_photo")
    var image: String,
    @Schema(description = "게시물 내용", example = "test_content")
    var content: String,
    @Schema(description = "좋아요 수", example = "0")
    var likes: Long,
    @Schema(description = "댓글 수", example = "0")
    var comments: Long,
)
