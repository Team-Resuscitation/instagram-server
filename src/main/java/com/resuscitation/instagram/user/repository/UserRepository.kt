package com.resuscitation.instagram.user.repository

import com.resuscitation.instagram.user.domain.Provider
import com.resuscitation.instagram.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {

    /**
     * OAuth2 제공자를 통해 유저 정보를 조회합니다.
     * @param provider 제공자
     * @return 유저 정보
     */
    fun findByProviders(provider: Provider): User?


    /**
     * 닉네임으로 유저 정보를 조회합니다.
     * @param nickname 닉네임
     * @return 존재 여부 (true: 존재, false: 미존재)
     */
    fun existsByNickname(nickname: String): Boolean

    /**
     * 이메일로 유저 정보를 조회합니다.
     * @param email 이메일
     * @return 존재 여부 (true: 존재, false: 미존재)
     */
    fun existsByEmail(email: String): Boolean

    /**
     * 전화번호로 유저 정보를 조회합니다.
     * @param phoneNumber 전화번호
     * @return 존재 여부 (true: 존재, false: 미존재)
     */
    fun existsByPhoneNumber(phoneNumber: String): Boolean


    fun findByEmail(email: String): User?

    fun findByNickname(nickname: String): User?

    fun findByPhoneNumber(phoneNumber: String): User?
}
