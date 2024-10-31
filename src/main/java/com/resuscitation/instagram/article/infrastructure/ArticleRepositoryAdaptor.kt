package com.resuscitation.instagram.article.infrastructure

import com.resuscitation.instagram.article.application.ArticleRepository
import com.resuscitation.instagram.article.domain.Article
import org.springframework.stereotype.Component

@Component
class ArticleRepositoryAdaptor(
    private val jpaArticleRepository: JpaArticleRepository
) : ArticleRepository {
    override fun findById(id: Long): Article? {
        return jpaArticleRepository.findById(id).orElse(null)
    }

    override fun save(articleEntity: Article): Article {
        return jpaArticleRepository.save(articleEntity)
    }
}
