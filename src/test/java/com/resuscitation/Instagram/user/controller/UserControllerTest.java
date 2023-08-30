package com.resuscitation.Instagram.user.controller;

import com.resuscitation.Instagram.user.dto.JwtDto;
import com.resuscitation.Instagram.user.dto.LoginFormDto;
import com.resuscitation.Instagram.user.dto.PasswordChangeFormDto;
import com.resuscitation.Instagram.user.dto.RegisterFormDto;
import com.resuscitation.Instagram.user.entity.UserEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.springframework.test.util.AssertionErrors.*;

@SpringBootTest
@DisplayName("유저 컨트롤러 - 통합테스트")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {

    @Autowired
    private UserController userController;

    String email = "test@test.com";
    String password = "Password1234@@@";
    String nickname = "test nickname";
    String name = "test name";
    String introduce = "test introduce";

    @BeforeAll
    void setup() {
        RegisterFormDto registerFormDto = new RegisterFormDto(email, password, nickname, name, introduce);
        ResponseEntity<JwtDto> response = userController.register(registerFormDto);
        System.out.println("BeforeAll 작동");
        System.out.println(registerFormDto.toString());
    }

    @Test
    @DisplayName("회원가입 - 성공")
    void register() {

        // GIVEN
        RegisterFormDto registerFormDto = new RegisterFormDto( "123asf"+email, password, nickname, name, introduce);
        System.out.println(registerFormDto.toString());

        // WHEN
        ResponseEntity<JwtDto> response = userController.register(registerFormDto);

        // THEN
        assert (response.getStatusCode().equals(HttpStatusCode.valueOf(200)));
        assertNotNull("회원가입 실패", response.getBody().getToken());
    }

    @Test
    @DisplayName("로그인 - 성공")
    void login() {
        LoginFormDto loginFormDto = new LoginFormDto(email, password);

        // When
        ResponseEntity<JwtDto> response = userController.login(loginFormDto);

        // THEN
        assert (response.getStatusCode().equals(HttpStatusCode.valueOf(200)));
        assertNotNull("로그인 실패", response.getBody().getToken());

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
        assert (response.getStatusCode().equals(HttpStatusCode.valueOf(200)));
        assertEquals("이메일이 일치하지 않습니다.", email, responseUser.getEmail());
        assertEquals("이름이 일치하지 않습니다.", name, responseUser.getName());
        assertNotEquals("비밀번호가 암호화되지 않았습니다.", password, responseUser.getPassword());

    }

    @Test
    @DisplayName("회원 탈퇴 - 성공")
    void deleteUser() {
        //Given
        LoginFormDto loginFormDto = new LoginFormDto(email, password);
        ResponseEntity<JwtDto> loginResponse = userController.login(loginFormDto);

        //When
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", loginResponse.getBody().getToken());
        ResponseEntity<UserEntity> response = userController.whois(request);
        UserEntity responseUser = response.getBody();

        //Then
        assert (response.getStatusCode().equals(HttpStatusCode.valueOf(200)));
        assertNotEquals("비밀번호가 암호화되지 않았습니다.", password, responseUser.getPassword());
    }

    @Test
    @DisplayName("회원 정보 수정 - 성공")
    void updateProfile() {
        //Given
        LoginFormDto loginFormDto = new LoginFormDto(email, password);
        ResponseEntity<JwtDto> loginResponse = userController.login(loginFormDto);

        //When
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", loginResponse.getBody().getToken());
        ResponseEntity<UserEntity> response = userController.whois(request);
        UserEntity responseUser = response.getBody();

        //Then
        assert (response.getStatusCode().equals(HttpStatusCode.valueOf(200)));
        assertNotEquals("비밀번호가 암호화되지 않았습니다.", password, responseUser.getPassword());
        assertNotNull("이메일은 비워둘 수 없습니다.", email);
        assertNotNull("이름은 비워둘 수 없습니다.", name);
    }

    @Test
    @DisplayName("비밀번호 변경 - 성공")
    void updatePassword() {
        //Given
        LoginFormDto loginFormDto = new LoginFormDto(email, password);
        ResponseEntity<JwtDto> loginResponse = userController.login(loginFormDto);
        String newPassword = "PassWord!!!@@@123";
        String rePassword = "PassWord!!!@@@123";
        PasswordChangeFormDto passwordChangeFormDto = new PasswordChangeFormDto(password, newPassword, rePassword);
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

        //When
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", loginResponse.getBody().getToken());
        ResponseEntity<UserEntity> response = userController.whois(request);
        UserEntity responseUser = response.getBody();

        //Then
        assert (response.getStatusCode().equals(HttpStatusCode.valueOf(200)));
        assertNotEquals("비밀번호가 암호화되지 않았습니다.", password, responseUser.getPassword());
        assertTrue("비밀번호는 반드시 한글자 이상의 대소문자 및 특수기호를 포함한 8자 이상으로 구성되어야 합니다.",
                newPassword.matches(passwordPattern));
        assertNotEquals("기존과 동일한 비밀번호입니다.",
                passwordChangeFormDto.getNewPassword(), responseUser.getPassword());
        assertEquals("비밀번호가 일치하지 않습니다.",
                rePassword, passwordChangeFormDto.getRePassword());
    }
}