package com.resuscitation.instagram.article.application

import com.resuscitation.instagram.article.domain.Article

interface ArticleRepository {
    fun findById(id: Long): Article?

    fun save(articleEntity: Article): Article
}
