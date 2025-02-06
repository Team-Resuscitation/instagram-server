package com.resuscitation.instagram.post.repository

import com.resuscitation.instagram.post.domain.Post
import com.resuscitation.instagram.post.dto.PostResponseDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository: JpaRepository<Post, Long> {
    fun findByIdx(idx: Long): Post
    fun deleteByIdx(idx: Long)
    fun findByAuthor(nickname: String): List<Post>
}