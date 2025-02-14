package com.resuscitation.instagram.security.auth.service

import com.resuscitation.instagram.exception.Exception
import com.resuscitation.instagram.exception.ExceptionCode
import com.resuscitation.instagram.security.auth.domain.User
import com.resuscitation.instagram.security.auth.dto.*
import com.resuscitation.instagram.security.auth.repository.UserRepository
import com.resuscitation.instagram.security.configuration.JwtProperties
import com.resuscitation.instagram.security.jwt.JwtProvider
import com.resuscitation.instagram.user.domain.Profile
import com.resuscitation.instagram.user.repository.ProfileRepository
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service

@Service
@EnableConfigurationProperties(JwtProperties::class)
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val passwordServiceImpl: PasswordServiceImpl,
    private val emailValidationServiceImpl: EmailValidationService,
    private val phoneNumberValidationServiceImpl: PhoneNumberValidationService,
    private val jwtProvider: JwtProvider,
    private val jwtProperties: JwtProperties,
    private val profileRepository: ProfileRepository,
) : AuthService {
    override fun register(registerRequestDto: RegisterRequestDto): RegisterResponseDto {
        // check nickname duplication
        if (userRepository.existsByNickname(registerRequestDto.nickname)) {
            return RegisterResponseDto(false, "Nickname already taken.")
        }

        // password encoding
        val encodedPassword = passwordServiceImpl.encodePassword(registerRequestDto.password)

        // check phoneNumber format, duplication
        val phoneNumber = registerRequestDto.phoneNumber ?: ""
        if (phoneNumber.isNotBlank()) {
            if (!phoneNumberValidationServiceImpl.formatCheck(phoneNumber)) {
                return RegisterResponseDto(false, "Invalid phone number.")
            }
            if (phoneNumberValidationServiceImpl.duplicationCheck(phoneNumber)) {
                return RegisterResponseDto(false, "Phone number already taken.")
            }
        }

        // check email format, duplication
        val email = registerRequestDto.email ?: ""
        if (email.isNotBlank()) {
            if (!emailValidationServiceImpl.formatCheck(email)) {
                return RegisterResponseDto(false, "Invalid email address.")
            }
            if (emailValidationServiceImpl.duplicationCheck(email)) {
                return RegisterResponseDto(false, "Email address already taken.")
            }
        }

        // sign up
        val user = userRepository.save(
            User(
                nickname = registerRequestDto.nickname,
                password = encodedPassword,
                phoneNumber = registerRequestDto.phoneNumber,
                email = registerRequestDto.email,
            )
        )
        val profile = profileRepository.save(
            Profile(
                nickname = registerRequestDto.nickname,
            )
        )

        return RegisterResponseDto(true, "User registered successfully.")
    }

    override fun login(loginRequestDto: LoginRequestDto): LoginResponseDto {
        // find user
        val user: User = when {
            // email check
            loginRequestDto.email != null
                -> userRepository.findByEmail(loginRequestDto.email)
            // nickname check
            loginRequestDto.nickname != null
                -> userRepository.findByNickname(loginRequestDto.nickname)
            // phone number check
            loginRequestDto.phoneNumber != null
                -> userRepository.findByPhoneNumber(loginRequestDto.phoneNumber)

            else -> throw Exception(ExceptionCode.INVALID_REQUEST)
        }

        // password check
        val encodedLoginFormPassword = passwordServiceImpl.encodePassword(loginRequestDto.password)
        if (passwordServiceImpl.verifyPassword(encodedLoginFormPassword, user.password)) {
            throw Exception(ExceptionCode.UNMATCHED_PASSWORD)
        }

        // create jwt
        val jwt = jwtProvider.generateToken(user, jwtProperties.accessExpiration)

        return LoginResponseDto(
            accessToken = jwt,
        )
    }

    override fun editUser(editUserRequestDto: EditUserRequestDto): EditUserResponseDto {
        val user = userRepository.findByNickname(editUserRequestDto.nickname)
        val editedUser = User(
            nickname = editUserRequestDto.nickname,
            email = editUserRequestDto.email ?: user.email,
            phoneNumber = editUserRequestDto.phoneNumber ?: user.phoneNumber,
        )
        userRepository.save(editedUser)
        return EditUserResponseDto(true, "User info edited successfully.")
    }

    override fun changePassword(passwordChangeFormDto: PasswordChangeRequestDto): PasswordChangeResponseDto {
        val user = userRepository.findByNickname(passwordChangeFormDto.nickname)
        val currentPassword = passwordChangeFormDto.currentPassword
        val newPassword = passwordChangeFormDto.newPassword
        val confirmPassword = passwordChangeFormDto.confirmPassword
        if (!passwordServiceImpl.verifyPassword(user.password, currentPassword)) {
            return PasswordChangeResponseDto(false, "Wrong password.")
        }
        if (newPassword != confirmPassword) {
            return PasswordChangeResponseDto(false, "Passwords do not match.")
        }
        userRepository.save(
            User(
                uuid = user.uuid,
                nickname = user.nickname,
                password = passwordServiceImpl.encodePassword(newPassword),
                phoneNumber = user.phoneNumber,
                email = user.email,
                userRole = user.userRole
            )
        )
        return PasswordChangeResponseDto(true, "Success changing password.")
    }

    override fun deleteUser(deleteUserRequestDto: DeleteUserRequestDto): DeleteUserResponseDto {
        val user = userRepository.findByNickname(deleteUserRequestDto.nickname)
        val encodedPassword = passwordServiceImpl.encodePassword(deleteUserRequestDto.password)
        passwordServiceImpl.verifyPassword(encodedPassword, user.password)
        return DeleteUserResponseDto(true, "Success deleting user.")
    }

    override fun logout(nickname: String): Boolean {
        jwtProvider.invalidateToken(nickname)
        return true
    }
}
