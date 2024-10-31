package com.resuscitation.instagram.article.domain

import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.Instant
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@EntityListeners(AuditingEntityListener::class)
class Article(
    id: Long = 0L,
    title: String = "",
    content: String,
    authorId: Long,
    createdAt: Instant = Instant.MIN,
    updatedAt: Instant = Instant.MIN,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = id

    var title: String = title
        protected set

    var content: String = content
        protected set

    var authorId: Long = authorId
        protected set

    @CreatedDate
    var createdAt: Instant = createdAt
        protected set

    @LastModifiedDate
    var updatedAt: Instant = updatedAt
        protected set

    companion object {
        fun create(title: String, content: String, authorId: Long): Article {
            return Article(
                title = title,
                content = content,
                authorId = authorId
            )
        }
    }

    fun update(title: String, content: String) {
        this.title = title
        this.content = content
    }
}
