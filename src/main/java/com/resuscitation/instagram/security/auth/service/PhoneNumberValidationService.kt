package com.resuscitation.instagram.security.auth.service

import com.resuscitation.instagram.security.auth.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class PhoneNumberValidationService(
    private val userRepository: UserRepository,
) : ValidationService {
    companion object {
        private val phoneRegex = Regex("^(01[016789]-?\\d{3,4}-?\\d{4}|0[2-9]-?\\d{3,4}-?\\d{4}|15\\d{2}-?\\d{4}|16\\d{2}-?\\d{4})\$")
    }

    override fun duplicationCheck(data: String): Boolean {
        return userRepository.existsByPhoneNumber(data)
    }

    override fun formatCheck(data: String): Boolean {
        return data.isNotBlank() && data.matches(phoneRegex)
    }
}
