package com.resuscitation.instagram.user.controller;

import com.resuscitation.instagram.user.dto.*;
import com.resuscitation.instagram.user.entity.UserEntity;
import com.resuscitation.instagram.user.service.UserService;
import com.resuscitation.instagram.user.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "로그인 기능 API")
@RestController
@RequestMapping("/user")
public class UserController {
    UserService userService;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userService = userServiceImpl;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Object nullex(Exception e) {
        if (e.getMessage().equals("ID_IS_NOT_AVAILABLE")) {
            return ResponseEntity.badRequest().body("사용할 수 없는 ID입니다.");
        }
        return "error";
    }

    /**
     * 회원 가입 Controller
     *
     * @param registerFormDto 회원 가입 폼 DTO
     * @return JWT
     */
    @PostMapping("/register")
    @Operation(summary = "회원가입 API")
    public ResponseEntity<JwtDto> register(
            @Valid @RequestBody RegisterFormDto registerFormDto
    ) {
        return ResponseEntity.ok(userService.register(registerFormDto));
    }

    /**
     * 로그인 Controller
     *
     * @param loginFormDto 로그인 DTO
     * @return JWT
     */
    @PostMapping("/login")
    @Operation(summary = "로그인 API")
    public ResponseEntity<JwtDto> login(
            @Valid @RequestBody LoginFormDto loginFormDto
    ) {
        return ResponseEntity.ok(userService.login(loginFormDto));
    }

    /**
     * 개인 정보 조회 API
     *
     * @param req HttpServletRequest
     * @return UserEntity 유저 정보
     */
    @GetMapping("/whois")
    public ResponseEntity<UserEntity> whois(
            HttpServletRequest req
    ) {
        UserEntity user = userService.whois(req);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(
            HttpServletRequest req
    ) {
        boolean result = userService.deleteUser(req);
        return ResponseEntity.ok("회원 탈퇴가 완료 되었습니다.");
    }

    @PatchMapping("/update")
    public ResponseEntity<UserEntity> updateProfile(
            HttpServletRequest req,
            @RequestBody EditProfileDto editProfileDto
    ) {
        UserEntity user = userService.editProfile(req, editProfileDto);

        return ResponseEntity.ok(user);
    }

    @PatchMapping("/updatePassword")
    public ResponseEntity<UserEntity> updatePassword(
            HttpServletRequest req,
            @RequestBody PasswordChangeFormDto passwordChangeFormDto
    ) {
        UserEntity user = userService.editPassword(req, passwordChangeFormDto);
        return ResponseEntity.ok(user);
    }
}
