package com.resuscitation.Instagram.user.repository;

import com.resuscitation.Instagram.article.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

    Optional<ArticleEntity> findByAuthor_Nickname(String nickname);

}
