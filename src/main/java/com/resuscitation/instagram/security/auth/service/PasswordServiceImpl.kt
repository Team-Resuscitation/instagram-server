package com.resuscitation.instagram.security.auth.service

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class PasswordServiceImpl(
    private val passwordEncoder: BCryptPasswordEncoder,
) : PasswordService {
    override fun encodePassword(password: String): String {
        return passwordEncoder.encode(password)
    }

    override fun verifyPassword(
        rawPassword: String,
        encodedPassword: String,
    ): Boolean {
        return passwordEncoder.matches(rawPassword, encodedPassword)
    }
}
