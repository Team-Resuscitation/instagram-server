package com.resuscitation.instagram.security.auth.repository

import com.resuscitation.instagram.security.auth.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User

    fun existsByEmail(email: String): Boolean

    fun findByNickname(nickname: String): User

    @Query("SELECT EXISTS (SELECT 1 FROM User WHERE nickname = :nickname) AS is_exist")
    fun existsByNickname(nickname: String): Boolean

    fun findByPhoneNumber(phoneNumber: String): User

    fun existsByPhoneNumber(phoneNumber: String): Boolean
}
