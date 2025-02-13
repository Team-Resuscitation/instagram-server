package com.resuscitation.instagram.user.repository

import com.resuscitation.instagram.user.domain.Profile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProfileRepository : JpaRepository<Profile, Long> {
    fun findByNickname(nickname: String): Optional<Profile>
}