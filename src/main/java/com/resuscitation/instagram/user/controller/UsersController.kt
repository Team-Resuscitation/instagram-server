package com.resuscitation.instagram.user.controller

import com.resuscitation.instagram.common.domain.AuthenticatedUser
import com.resuscitation.instagram.config.SwaggerConfig.Companion.BEARER_AUTH
import com.resuscitation.instagram.config.SwaggerConfig.Companion.USERS_API
import com.resuscitation.instagram.user.controller.data.PasswordChangeForm
import com.resuscitation.instagram.user.domain.User
import com.resuscitation.instagram.user.dto.UserEditForm
import com.resuscitation.instagram.user.service.UserService
import com.resuscitation.instagram.user.service.data.ProfileData
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v2/users")
@Tag(name = USERS_API, description = "User Info APIs")
class UsersController(
    private val userService: UserService,
) {
    @Operation(
        summary = "View user profile",
        description = "Fetches the profile details of a user by their nickname.",
    )
    @GetMapping("/{nickname}")
    fun showProfile(
        @PathVariable nickname: String,
    ): ResponseEntity<ProfileData> {
        return ResponseEntity.ok(
            userService.getProfile(nickname),
        )
    }

    @PatchMapping("/{userId}")
    @PreAuthorize("#userId == #authenticatedUser.userId")
    @Operation(summary = "사용자 정보 수정")
    @SecurityRequirement(name = BEARER_AUTH)
    fun editUser(
        @PathVariable userId: Long,
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
        @RequestBody userEditForm: UserEditForm,
    ): ResponseEntity<User> {
        TODO("Not yet implemented")
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("#userId == #authenticatedUser.userId")
    @Operation(summary = "사용자 정보 삭제")
    @SecurityRequirement(name = BEARER_AUTH)
    fun deleteUser(
        @PathVariable userId: Long,
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
    ): ResponseEntity<User> {
        userService.withdraw(authenticatedUser.userId)
        return ResponseEntity.noContent().build()
    }

    @PatchMapping("/{userId}/password")
    @PreAuthorize("#userId == #authenticatedUser.userId")
    @Operation(
        summary = "비밀번호 변경",
        description = """
<h2>현재 비밀번호를 입력받아 새로운 비밀번호로 변경합니다.</h2>
<p>소셜로그인으로 회원가입 한 경우, currentPassword는 null로 보냅니다.</p>
""",
    )
    @SecurityRequirement(name = BEARER_AUTH)
    fun changePassword(
        @PathVariable userId: Long,
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
        @RequestBody passwordChangeForm: PasswordChangeForm,
    ): ResponseEntity<Unit> {
        userService.changePassword(
            userId = authenticatedUser.userId,
            currentPassword = passwordChangeForm.currentPassword,
            newPassword = passwordChangeForm.newPassword,
            confirmPassword = passwordChangeForm.confirmPassword,
        )
        return ResponseEntity.ok().build()
    }

    @PostMapping("/{userId}/profile-image", consumes = [MULTIPART_FORM_DATA_VALUE])
    @PreAuthorize("#userId == #authenticatedUser.userId")
    @Operation(summary = "프로필 이미지 변경")
    @SecurityRequirement(name = BEARER_AUTH)
    fun changeProfileImage(
        @PathVariable userId: Long,
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
        image: MultipartFile,
    ): ResponseEntity<Unit> {
        userService.changeProfileImage(authenticatedUser.userId, image)
        return ResponseEntity.ok().build()
    }
}
