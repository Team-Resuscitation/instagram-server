package com.resuscitation.Instagram.user.controller;

import com.resuscitation.Instagram.user.dto.ProfileDto;
import com.resuscitation.Instagram.user.service.ProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "프로필 피드 응답 API")
@RestController
public class ProfileController {

    @Autowired
    public ProfileController(ProfileService profileServiceImpl) {
        this.profileService = profileServiceImpl;
    }

    private ProfileService profileService;

    /**
     * 개인 피드 조회 Controller
     *
     * @param nickname
     * @return 회원 정보, 게시글 리스트
     */
    @GetMapping("/{nickname}")
    public ResponseEntity<ProfileDto> getUserProfile(@PathVariable("nickname") String nickname) {
        return ResponseEntity.ok(profileService.showProfile(nickname));
    }

}
