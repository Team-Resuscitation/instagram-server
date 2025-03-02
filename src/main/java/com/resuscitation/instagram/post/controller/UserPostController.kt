package com.resuscitation.instagram.post.controller

import com.resuscitation.instagram.config.SwaggerConfig.Companion.USERS_API
import com.resuscitation.instagram.post.domain.Post
import com.resuscitation.instagram.post.service.PostService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = USERS_API, description = "User Post Controller APIs")
@RestController
@RequestMapping("/api/v1/users/{nickname}")
class UserPostController(
    private val postService: PostService,
) {
    @GetMapping("/posts")
    fun getUserPosts(
        @PathVariable nickname: String,
        @PageableDefault(
            size = 9,
            sort = ["createdAt"],
            direction = Sort.Direction.DESC,
        )
        @ParameterObject pageable: Pageable,
    ): Page<Post> {
        return postService.findPostsByUser(nickname, pageable)
    }
}
