package com.resuscitation.Instagram.user.service;

import com.resuscitation.Instagram.user.dto.ProfileDto;

public interface ProfileService {
    /**
     * 유저 프로필 반환 메서드
     *
     * @param nickname 검색할 사람의 아이디
     * @return ProfileDto (프로필 + 게시글 리스트)
     * @author oyune
     */
    ProfileDto showProfile(String nickname);
}
