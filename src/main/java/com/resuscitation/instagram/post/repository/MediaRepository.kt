package com.resuscitation.instagram.post.repository

import com.resuscitation.instagram.post.domain.Media
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MediaRepository : JpaRepository<Media, Long> {
    fun findByPostId(postId: Long): List<Media>
}