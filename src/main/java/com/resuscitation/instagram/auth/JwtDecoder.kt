package com.resuscitation.instagram.auth

import com.resuscitation.instagram.common.domain.AuthenticatedUser

interface JwtDecoder {
    /**
     * 토큰을 디코딩합니다.
     * @param token 토큰
     * @return 인증 정보
     */
    fun decode(token: String): AuthenticatedUser
}
