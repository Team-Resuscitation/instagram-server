package com.resuscitation.Instagram.user.service;

import com.resuscitation.Instagram.jwt.JwtTokenProvider;
import com.resuscitation.Instagram.user.dto.JwtDto;
import com.resuscitation.Instagram.user.dto.LoginFormDto;
import com.resuscitation.Instagram.user.dto.RegisterFormDto;
import com.resuscitation.Instagram.user.entity.UserEntity;
import com.resuscitation.Instagram.user.entity.UserRole;
import com.resuscitation.Instagram.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    JwtTokenProvider jwtTokenProvider;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public JwtDto register(RegisterFormDto registerFormDto) throws IllegalArgumentException {

        // 아이디 중복 확인
        if (userRepository.existsByEmail(registerFormDto.getEmail())) {
            throw new IllegalArgumentException("중복 이메일");
        }

        UserEntity user = new UserEntity();
        user.setEmail(registerFormDto.getEmail());
        user.setIntroduce(registerFormDto.getIntroduce());
        user.setName(registerFormDto.getName());
        user.setNickname(registerFormDto.getNickname());
        user.setIntroduce(registerFormDto.getNickname());

        // 비밀번호 암호화
        user.setPassword(passwordEncoder.encode(registerFormDto.getPassword()));

        UserEntity saved = userRepository.save(user);

        List<UserRole> roles = new ArrayList<>();
        roles.add(UserRole.ROLE_CLIENT);

        // Json으로 감싸기
        JwtDto jwtDto = new JwtDto();
        jwtDto.setToken("Bearer "+jwtTokenProvider.createToken(saved,roles ));

        return jwtDto;
    }

    @Override
    public boolean emailDuplicateCheck(String email) {
        return !userRepository.existsByEmail(email);
    }

    @Override
    public String registerWithFaceBook(String facebookCode) {
        // TODO : Facebook Oauth 서치 후 구현
        return null;
    }

    @Override
    public JwtDto login(LoginFormDto loginFormDto) {
        Optional<UserEntity> user = userRepository.findByEmail(loginFormDto.getEmail());

        if (user.isEmpty()) {
            throw new IllegalArgumentException("가입되지 않은 이메일입니다.");
        } else if (!passwordEncoder.matches(loginFormDto.getPassword(), user.get().getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // TODO: Security 작업 때 UserRole 추가하기
        List<UserRole> roles = new ArrayList<>();
        roles.add(UserRole.ROLE_CLIENT);

        // 객체로 변환하기
        JwtDto jwtDto = new JwtDto();
        jwtDto.setToken("Bearer "+jwtTokenProvider.createToken(user.get(),roles ));


        return jwtDto;
    }

    @Override
    public String loginWithFacebook(String facebookCode) {
        // TODO : Facebook Oauth 서치 후 구현
        return null;
    }

    @Override
    public UserEntity whois(HttpServletRequest token) {

        Long userIdx = jwtTokenProvider.getUid(token);

        return userRepository.findById(userIdx).orElseThrow(null);
    }
}
