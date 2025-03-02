package com.resuscitation.instagram.auth.service

import com.resuscitation.instagram.auth.TokenProvider
import com.resuscitation.instagram.auth.controller.data.LoginRequest
import com.resuscitation.instagram.auth.controller.data.RegisterRequest
import com.resuscitation.instagram.auth.properties.JwtProperties
import com.resuscitation.instagram.user.domain.User
import com.resuscitation.instagram.user.repository.UserRepository
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
@EnableConfigurationProperties(JwtProperties::class)
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val tokenProvider: TokenProvider,
) {
    fun register(registerRequest: RegisterRequest): String {
        // Email, nickname, phoneNumber 중복 확인
        if (userRepository.existsByEmail(registerRequest.email)) {
            throw IllegalArgumentException("Email already exists.")
        }

        if (userRepository.existsByNickname(registerRequest.nickname)) {
            throw IllegalArgumentException("Nickname already exists.")
        }

        if (userRepository.existsByPhoneNumber(registerRequest.phoneNumber)) {
            throw IllegalArgumentException("Phone number already exists.")
        }

        // password encoding
        val encodedPassword = passwordEncoder.encode(registerRequest.password)

        val user =
            userRepository.save(
                User(
                    email = registerRequest.email,
                    nickname = registerRequest.nickname,
                    password = encodedPassword,
                    phoneNumber = registerRequest.phoneNumber,
                ),
            )

        return tokenProvider.encode(user)
    }

    fun login(loginRequest: LoginRequest): String {
        // 유저를 검색할 때 이메일, 닉네임, 전화번호 순으로 검색
        val user: User =
            userRepository.findByEmail(loginRequest.key)
                ?: userRepository.findByNickname(loginRequest.key)
                ?: userRepository.findByPhoneNumber(loginRequest.key)
                ?: throw IllegalArgumentException("User not found.")

        // 패스워드 확인
        if (!passwordEncoder.matches(loginRequest.password, user.password)) {
            throw IllegalArgumentException("Password is incorrect.")
        }

        return tokenProvider.encode(user)
    }
}
