package com.resuscitation.instagram.user.service

import com.resuscitation.instagram.common.storage.StorageService
import com.resuscitation.instagram.user.domain.User
import com.resuscitation.instagram.user.repository.UserRepository
import com.resuscitation.instagram.user.service.data.ProfileData
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val storageService: StorageService,
) {
    /**
     * 패스워드 변경
     * @param userId 유저 ID
     * @param currentPassword 현재 패스워드
     * @param newPassword 새로운 패스워드
     * @param confirmPassword 새로운 패스워드 확인
     */
    @Transactional
    fun changePassword(
        userId: Long,
        currentPassword: String?,
        newPassword: String,
        confirmPassword: String,
    ): Boolean {
        val user = userRepository.findById(userId).orElseThrow { IllegalArgumentException("User not found.") }
        // 소셜 회원가입 유저는 패스워드가 Null 일 수 있음
        // 따라서 패스워드가 Null 이 아닐 때만 비교
        user.password?.let {
            if (!passwordEncoder.matches(currentPassword, it)) {
                throw IllegalArgumentException("Wrong password.")
            }
        }

        if (newPassword != confirmPassword) {
            throw IllegalArgumentException("Passwords do not match.")
        }
        user.password = passwordEncoder.encode(newPassword)

        return true
    }

    /**
     * 프로필 이미지 변경
     * @param userId 유저 ID
     * @param image 변경할 이미지
     * @return 변경된 이미지 URL
     */
    @Transactional
    fun changeProfileImage(
        userId: Long,
        image: MultipartFile,
    ): String {
        val user: User = userRepository.findById(userId).orElseThrow { IllegalArgumentException("User not found.") }
        val imageUrl = storageService.upload(image)
        user.profileImage = imageUrl
        return imageUrl
    }

    @Transactional(readOnly = true)
    fun getProfile(nickname: String): ProfileData {
        val user =
            userRepository.findByNickname(nickname)
                ?: throw IllegalArgumentException("User not found")
        return ProfileData.from(user)
    }

    @Transactional
    fun withdraw(userId: Long) {
        val user = userRepository.findById(userId).orElseThrow { IllegalArgumentException("User not found.") }
        userRepository.delete(user)
    }
}
