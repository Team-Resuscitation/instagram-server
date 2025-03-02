package com.resuscitation.instagram.user.controller

import com.resuscitation.instagram.common.domain.AuthenticatedUser
import com.resuscitation.instagram.config.SwaggerConfig
import com.resuscitation.instagram.config.SwaggerConfig.Companion.USER_API
import com.resuscitation.instagram.user.domain.User
import com.resuscitation.instagram.user.repository.UserRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = USER_API, description = "로그인 한 사용자 정보")
@RestController
@RequestMapping("/api/v2/user")
@SecurityRequirement(name = SwaggerConfig.BEARER_AUTH)
class UserController(
    private val userRepository: UserRepository,
) {
    @GetMapping
    @Operation(summary = "사용자 정보 조회")
    fun getUser(
        @AuthenticationPrincipal user: AuthenticatedUser,
    ): ResponseEntity<User> {
        return ResponseEntity.ok(
            userRepository.findById(user.userId).get()
        )
    }
}
