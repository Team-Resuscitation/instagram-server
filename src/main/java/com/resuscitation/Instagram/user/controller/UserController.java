package com.resuscitation.Instagram.user.controller;

import com.resuscitation.Instagram.user.dto.JwtDto;
import com.resuscitation.Instagram.user.dto.LoginFormDto;
import com.resuscitation.Instagram.user.dto.RegisterFormDto;
import com.resuscitation.Instagram.user.entity.UserEntity;
import com.resuscitation.Instagram.user.service.UserService;
import com.resuscitation.Instagram.user.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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
    @Operation(summary = "회원가입 API" )
    public ResponseEntity<JwtDto> register(
            @RequestBody RegisterFormDto registerFormDto
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
            @RequestBody LoginFormDto loginFormDto
    ) {
        return ResponseEntity.ok(userService.login(loginFormDto));
    }

    /**
     * 개인 정보 조회 API
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

}
