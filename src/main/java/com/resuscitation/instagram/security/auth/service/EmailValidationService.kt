package com.resuscitation.instagram.security.auth.service

import com.resuscitation.instagram.security.auth.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class EmailValidationService(
    private val userRepository: UserRepository,
) : ValidationService {
    companion object {
        private val emailRegex = Regex("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}\$") // 이메일 유효성
    }

    override fun duplicationCheck(data: String): Boolean {
        return userRepository.existsByEmail(data)
    }

    override fun formatCheck(data: String): Boolean {
        return data.isNotBlank() && data.matches(emailRegex)
    }
}