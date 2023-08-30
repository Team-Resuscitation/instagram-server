package com.resuscitation.Instagram.user.service;

import com.resuscitation.Instagram.jwt.JwtTokenProvider;
import com.resuscitation.Instagram.user.dto.*;
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
//        if (userRepository. existsByEmail(registerFormDto.getEmail())) {
//            throw new IllegalArgumentException("중복 이메일");
//        }
        if (userRepository.findByEmail(registerFormDto.getEmail()).isPresent()){
            System.out.println(userRepository.findByEmail(registerFormDto.getEmail()));
            throw new IllegalArgumentException("중복 이메일");
        }

        // Client Role 설정
        List<UserRole> roles = new ArrayList<>();
        roles.add(UserRole.ROLE_CLIENT);

        UserEntity user = new UserEntity();
        user.setEmail(registerFormDto.getEmail());
        user.setIntroduce(registerFormDto.getIntroduce());
        user.setName(registerFormDto.getName());
        user.setNickname(registerFormDto.getNickname());
        user.setIntroduce(registerFormDto.getNickname());
        user.setRoles(roles);

        // 비밀번호 암호화
        user.setPassword(passwordEncoder.encode(registerFormDto.getPassword()));

        UserEntity saved = userRepository.save(user);

        // Json으로 감싸기
        JwtDto jwtDto = new JwtDto();
        jwtDto.setToken("Bearer " + jwtTokenProvider.createToken(saved, saved.getRoles()));

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

        // 객체로 변환하기
        JwtDto jwtDto = new JwtDto();
        jwtDto.setToken("Bearer " + jwtTokenProvider.createToken(user.get(), user.get().getRoles()));

        return jwtDto;
    }

    @Override
    public String loginWithFacebook(String facebookCode) {
        // TODO : Facebook Oauth 서치 후 구현
        return null;
    }

    @Override
    public UserEntity whois(HttpServletRequest req) {

        Long userIdx = jwtTokenProvider.getUid(req);

        return userRepository.findById(userIdx).orElseThrow(null);
    }

    @Override
    public boolean deleteUser(HttpServletRequest req) {
        Long userIdx = jwtTokenProvider.getUid(req);
        UserEntity user = userRepository.findById(userIdx).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        userRepository.deleteById(userIdx);

        return true;
    }

    @Override
    public UserEntity editProfile(HttpServletRequest req, EditProfileDto editProfileDto) {
        UserEntity user = userRepository.findById(jwtTokenProvider.getUid(req)).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );

        // 유저 정보 업데이트
        user.setName(editProfileDto.getName());
        user.setIntroduce(editProfileDto.getIntroduce());
        if (!editProfileDto.getNickname().isEmpty()) user.setNickname(editProfileDto.getNickname());

        // User Data Save
        return userRepository.save(user);
    }

    @Override
    public UserEntity editPassword(HttpServletRequest req, PasswordChangeFormDto passwordChangeFormDto) {
        UserEntity user = userRepository.findById(jwtTokenProvider.getUid(req)).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );

        // 현재 비밀번호를 재확인
        if (!passwordEncoder.matches(user.getPassword(), passwordChangeFormDto.getCurrentPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        
        // 새로운 비밀번호와 현재 비밀번호가 일치하는지 확인
         if (!passwordEncoder.matches(user.getPassword(), passwordChangeFormDto.getNewPassword())) {
            throw new IllegalArgumentException("현재 비밀번호와 일치합니다.");
        }

        // 새 비밀번호와 재입력한 새로운 비밀번호 대조
        if (passwordChangeFormDto.getNewPassword().equals(passwordChangeFormDto.getRePassword())) {
            throw new IllegalArgumentException("새 비밀번호가 일치하지 않습니다.");
        }

        // Password Update
        user.setPassword(passwordEncoder.encode(passwordChangeFormDto.getNewPassword()));

        return userRepository.save(user);
    }
}
