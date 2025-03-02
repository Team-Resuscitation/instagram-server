package com.resuscitation.instagram.auth

import com.resuscitation.instagram.user.domain.User

interface JwtEncoder {
    /**
     * 토큰 생성
     * @param userId 사용자 ID
     * @param roles 사용자 권한
     * @return 생성된 토큰
     */
    fun encode(
        user: User,
    ): String
}
