package com.resuscitation.instagram.user.service;

import com.resuscitation.instagram.article.domain.Article;
import com.resuscitation.instagram.user.dto.ProfileDto;
import com.resuscitation.instagram.user.entity.UserEntity;
import com.resuscitation.instagram.article.infrastructure.ArticleRepository;
import com.resuscitation.instagram.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileService {

    UserRepository userRepository;
    ArticleRepository articleRepository;

    @Autowired
    public ProfileServiceImpl(UserRepository userRepository, ArticleRepository articleRepository) {
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public ProfileDto showProfile(String nickname) {
        // User Repository에서 유저 정보 받아오기
        UserEntity userEntity = userRepository.findByNickname(nickname).orElseThrow();
        // Article repository 로부터 게시글 리스트 받아오기
        Optional<Article> articleEntity = articleRepository.findByAuthor_Nickname(nickname);
        ArrayList<String> postArray = new ArrayList<>();
        articleEntity.ifPresent(tmp -> postArray.add(tmp.getImage()));
        //유저 정보와 게시글 리스트 반환
        return new ProfileDto(userEntity, postArray);
    }
}
