package com.resuscitation.instagram.post.service

import com.resuscitation.instagram.post.dto.PostRequestDto
import com.resuscitation.instagram.post.dto.PostResponseDto

interface PostService {
    fun uploadPost(postRequestDto: PostRequestDto): Boolean
    fun readPost(postId: String): PostResponseDto
    fun updatePost(postId: String, postRequestDto: PostRequestDto): Boolean
    fun deletePost(postId: String): Boolean
    fun timeline(nickname: String): List<PostResponseDto>
}