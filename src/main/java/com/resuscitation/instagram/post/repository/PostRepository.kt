package com.resuscitation.instagram.post.repository

import com.resuscitation.instagram.post.domain.Post
import com.resuscitation.instagram.post.dto.PostResponseDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PostRepository: JpaRepository<Post, Long> {
    fun findByIdx(idx: Long): Post

    fun deleteByIdx(idx: Long)

    fun findByAuthor(nickname: String): List<Post>

    @Query("SELECT p FROM Post p WHERE p.idx IN (SELECT t.postIdx FROM Timeline t WHERE t.nickname IN (SELECT pr.nickname FROM Profile pr WHERE pr.follower >= 1000) AND t.timestamp > (SELECT pr.lastAccess FROM Profile pr WHERE pr.nickname = t.nickname))")
    fun findPostsOfInfluencers(): List<Post>

    @Query("SELECT p FROM Post p WHERE p.author IN (SELECT pr.nickname FROM Profile pr WHERE pr.follower < 1000) AND p.timestamp > (SELECT pr.lastAccess FROM Profile pr WHERE pr.nickname = p.author)")
    fun findPostsOfGeneralUsers(): List<Post>
}