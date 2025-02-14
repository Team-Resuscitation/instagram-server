package com.resuscitation.instagram.security.auth.service

import com.resuscitation.instagram.security.auth.dto.*

interface AuthService {
    fun register(registerRequestDto: RegisterRequestDto): RegisterResponseDto

    fun login(loginRequestDto: LoginRequestDto): LoginResponseDto

    fun editUser(editUserRequestDto: EditUserRequestDto): EditUserResponseDto

    fun changePassword(passwordChangeFormDto: PasswordChangeRequestDto): PasswordChangeResponseDto

    fun deleteUser(deleteUserRequestDto: DeleteUserRequestDto): DeleteUserResponseDto

    fun logout(nickname: String): Boolean
}
