package com.resuscitation.instagram.security.auth.service

import com.resuscitation.instagram.security.auth.dto.DeleteUserRequestDto
import com.resuscitation.instagram.security.auth.dto.DeleteUserResponseDto
import com.resuscitation.instagram.security.auth.dto.EditUserRequestDto
import com.resuscitation.instagram.security.auth.dto.EditUserResponseDto
import com.resuscitation.instagram.security.auth.dto.LoginRequestDto
import com.resuscitation.instagram.security.auth.dto.LoginResponseDto
import com.resuscitation.instagram.security.auth.dto.PasswordChangeRequestDto
import com.resuscitation.instagram.security.auth.dto.PasswordChangeResponseDto
import com.resuscitation.instagram.security.auth.dto.RegisterRequestDto
import com.resuscitation.instagram.security.auth.dto.RegisterResponseDto

interface AuthService {
    fun register(registerRequestDto: RegisterRequestDto): RegisterResponseDto

    fun login(loginRequestDto: LoginRequestDto): LoginResponseDto

    fun editUser(editUserRequestDto: EditUserRequestDto): EditUserResponseDto

    fun changePassword(passwordChangeFormDto: PasswordChangeRequestDto): PasswordChangeResponseDto

    fun deleteUser(deleteUserRequestDto: DeleteUserRequestDto): DeleteUserResponseDto

    fun logout(nickname: String): Boolean
}
