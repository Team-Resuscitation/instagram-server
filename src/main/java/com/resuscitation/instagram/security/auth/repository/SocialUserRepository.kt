package com.resuscitation.instagram.security.auth.repository

import com.resuscitation.instagram.security.auth.domain.SocialUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SocialUserRepository : JpaRepository<SocialUser, Long> {
    fun findByProviderId(providerId: String): SocialUser
}
