package com.resuscitation.Instagram.article.dto

import com.resuscitation.Instagram.user.entity.UserEntity
import io.swagger.v3.oas.annotations.media.Schema

class ArticleDto(
        @Schema(description = "게시물 번호", example = "1")
        var idx: Long,

        @Schema(description = "게시물 작성자", example = "tester_nickname")
        var author: UserEntity,

        @Schema(description = "사진", example = "test_photo")
        var image: String,

        @Schema(description = "게시물 내용", example = "test_content")
        var content: String,

        @Schema(description = "좋아요 수", example = "0")
        var likes: Long,

        @Schema(description = "댓글 수", example = "0")
        var comments: Long,
) {
}