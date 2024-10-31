package com.resuscitation.instagram.article.infrastructure

import com.resuscitation.instagram.article.domain.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaArticleRepository : JpaRepository<Article, Long> {

}
