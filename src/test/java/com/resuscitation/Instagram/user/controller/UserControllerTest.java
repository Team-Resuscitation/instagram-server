package com.resuscitation.Instagram.user.controller;

import com.resuscitation.Instagram.user.dto.JwtDto;
import com.resuscitation.Instagram.user.dto.LoginFormDto;
import com.resuscitation.Instagram.user.dto.RegisterFormDto;
import com.resuscitation.Instagram.user.entity.UserEntity;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.springframework.test.util.AssertionErrors.*;

@SpringBootTest
@DisplayName("유저 컨트롤러 - 통합테스트")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {

    @Autowired
    private UserController userController;

    String email = "test@test.com";
    String password = "Password!!!";
    String nickname = "test nickname";
    String name = "test name";
    String introduce = "test introduce";

    @BeforeAll
    void setup() {
        RegisterFormDto registerFormDto = new RegisterFormDto(email, password, nickname, name, introduce);
        ResponseEntity<JwtDto> response = userController.register(registerFormDto);
    }

    @Test
    @DisplayName("회원가입 - 성공")
    void register() {

        // GIVEN
        RegisterFormDto registerFormDto = new RegisterFormDto(email + "123", password, nickname, name, introduce);

        // WHEN
        ResponseEntity<JwtDto> response = userController.register(registerFormDto);

        // THEN
        assert (response.getStatusCode().equals(HttpStatusCode.valueOf(200)));
        assertNotNull("", response.getBody().getToken());
    }

    @Test
    @DisplayName("로그인 - 성공")
    void login() {
        LoginFormDto loginFormDto = new LoginFormDto(email, password);

        // When
        ResponseEntity<JwtDto> response = userController.login(loginFormDto);

        // THEN
        assert (response.getStatusCode().equals(HttpStatusCode.valueOf(200)));
        assertNotNull("", response.getBody().getToken());

    }

    @Test
    @DisplayName("개인정보 조회 - 성공")
    void whois() {
        // Given
        LoginFormDto loginFormDto = new LoginFormDto(email, password);
        ResponseEntity<JwtDto> loginResponse = userController.login(loginFormDto);

        // When
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", loginResponse.getBody().getToken());
        ResponseEntity<UserEntity> response = userController.whois(request);
        UserEntity responseUser = response.getBody();

        // Then
        assertEquals("이메일이 일치하지 않습니다.", email, responseUser.getEmail());
        assertEquals("이름이 일치하지 않습니다.", name, responseUser.getName());
        assertNotEquals("비밀번호가 암호화 되지 않았습니다.", password, responseUser.getPassword());

    }

    @Disabled("Disabled until implement")
    @Test
    @DisplayName("회원 탈퇴 - 성공")
    void deleteUser() {
    }

    @Disabled("Disabled until implement")
    @Test
    @DisplayName("회원 정보 수정 - 성공")
    void updateProfile() {
    }

    @Disabled("Disabled until implement")
    @Test
    @DisplayName("비밀번호 변경 - 성공")
    void updatePassword() {
    }
}