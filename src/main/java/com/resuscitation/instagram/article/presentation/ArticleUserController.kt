package com.resuscitation.instagram.article.presentation

import com.resuscitation.instagram.article.application.ArticleService
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleUserController(
    private val articleService: ArticleService,
)
