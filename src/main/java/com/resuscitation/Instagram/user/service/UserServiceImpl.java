package com.resuscitation.Instagram.user.service;

import com.resuscitation.Instagram.user.dto.LoginFormDto;
import com.resuscitation.Instagram.user.dto.RegisterFormDto;
import com.resuscitation.Instagram.user.entity.UserEntity;
import com.resuscitation.Instagram.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String register(RegisterFormDto registerFormDto) throws IllegalArgumentException {

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

        return saved.toString();
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
    public String login(LoginFormDto loginFormDto) {
        Optional<UserEntity> user = userRepository.findByEmail(loginFormDto.getEmail());

        if (user.isEmpty()) {
            throw new IllegalArgumentException("가입되지 않은 이메일입니다.");
        } else if (!passwordEncoder.matches(loginFormDto.getPassword(), user.get().getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return user.get().toString();

    }

    @Override
    public String loginWithFacebook(String facebookCode) {
        // TODO : Facebook Oauth 서치 후 구현
        return null;
    }
}
