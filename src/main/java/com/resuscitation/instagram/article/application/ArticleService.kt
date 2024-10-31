package com.resuscitation.instagram.article.application

import com.resuscitation.instagram.article.domain.Article
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleService(
    private val articleRepository: ArticleRepository
) {
    @Transactional(readOnly = true)
    fun getArticle(id: Long): Article {
        val article: Article = articleRepository.findById(id) ?: throw IllegalArgumentException()
        return article
    }

    @Transactional
    fun createArticle() {
        val article = Article.create(
            title = "title",
            content = "content",
            authorId = 1L
        )
        articleRepository.save(article)
    }

    @Transactional
    fun updateArticle(
        id: Long,
        title: String,
        content: String
    ) {
        val article: Article = articleRepository.findById(id) ?: throw IllegalArgumentException()

        article.update(
            title = title,
            content = content
        )
    }
}
