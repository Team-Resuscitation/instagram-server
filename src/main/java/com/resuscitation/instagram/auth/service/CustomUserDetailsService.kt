package com.resuscitation.instagram.auth.service

import com.resuscitation.instagram.auth.UserPrincipal
import com.resuscitation.instagram.user.domain.User
import com.resuscitation.instagram.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository,
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserPrincipal? {
        if (username == null) {
            return null
        }

        val user: User? = userRepository.findByIdOrNull(
            username.toLong()
        )

        return user?.let {
            UserPrincipal.create(it)
        }
    }
}
