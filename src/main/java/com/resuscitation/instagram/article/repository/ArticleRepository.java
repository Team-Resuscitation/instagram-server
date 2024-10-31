package com.resuscitation.instagram.article.repository;

import com.resuscitation.instagram.article.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

    Optional<ArticleEntity> findByAuthor_Nickname(String nickname);

}
