package com.resuscitation.Instagram.article.entity

import com.resuscitation.Instagram.user.entity.UserEntity
import jakarta.persistence.*

@Entity
class ArticleEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var idx: Long = 0,

        @ManyToOne
        var author: UserEntity = UserEntity(),

        var image: String = "",

        @Column(columnDefinition = "LONGTEXT")
        var content: String = "",

        var likes: Long = 0,

        var comments: Long = 0,
) {
}