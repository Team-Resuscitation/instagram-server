package com.resuscitation.instagram.security.auth.controller

import com.resuscitation.instagram.config.SwaggerConfig
import com.resuscitation.instagram.security.auth.dto.DeleteUserRequestDto
import com.resuscitation.instagram.security.auth.dto.DeleteUserResponseDto
import com.resuscitation.instagram.security.auth.dto.EditUserRequestDto
import com.resuscitation.instagram.security.auth.dto.EditUserResponseDto
import com.resuscitation.instagram.security.auth.dto.LoginRequestDto
import com.resuscitation.instagram.security.auth.dto.LoginResponseDto
import com.resuscitation.instagram.security.auth.dto.RegisterRequestDto
import com.resuscitation.instagram.security.auth.dto.RegisterResponseDto
import com.resuscitation.instagram.security.auth.service.AuthServiceImpl
import com.resuscitation.instagram.security.auth.service.CustomOAuth2UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v2/auth")
@Tag(name = "Auth", description = "User Auth APIs")
class AuthController(
    private val authServiceImpl: AuthServiceImpl,
    private val customOAuth2UserService: CustomOAuth2UserService,
) {
    @Operation(
        summary = "User register api",
        description = "Sign up for Instagram using a username, email, and password.",
        responses = [
            ApiResponse(responseCode = "200", description = "User registered successfully"),
        ]
    )
    @PostMapping("/instagram/register")
    fun instagramRegister(
        @Valid @RequestBody @Parameter(description = "User registration request body") registerRequestDto: RegisterRequestDto
    ): ResponseEntity<RegisterResponseDto> {
        return ResponseEntity.ok(authServiceImpl.register(registerRequestDto))
    }

    @Operation(
        summary = "User login api",
        description = "Log in to Instagram using email and password.",
        responses = [
            ApiResponse(responseCode = "200", description = "User login successful"),
        ]
    )
    @PostMapping("/instagram/login")
    fun instagramLogin(
        @Valid @RequestBody @Parameter(description = "User login request body") loginRequestDto: LoginRequestDto
    ): ResponseEntity<LoginResponseDto> {
        return ResponseEntity.ok(authServiceImpl.login(loginRequestDto))
    }

    @Operation(
        summary = "Google social login api",
        description = "Sign up or log in using Google OAuth2.",
        responses = [
            ApiResponse(responseCode = "200", description = "User registered successfully"),
        ]
    )
    @GetMapping("/google/{provider}/callback")
    fun googleCallback(
        @AuthenticationPrincipal @Parameter(hidden = true) user: OAuth2User,
        @PathVariable @Parameter(description = "Social login provider") provider: String
    ): ResponseEntity<LoginResponseDto> {
        return ResponseEntity.ok(customOAuth2UserService.loadUser(user))
    }

    @Operation(
        summary = "Edit user information",
        description = "Allows a user to update their profile information, such as nickname or other details.",
        responses = [
            ApiResponse(responseCode = "200", description = "User information updated successfully"),
        ]
    )
    @PostMapping("/edit")
    @SecurityRequirement(name = SwaggerConfig.BEARER_AUTH)
    fun editUser(
        @AuthenticationPrincipal @Parameter(hidden = true) user: OAuth2User,
        @Valid @RequestBody @Parameter(description = "User nickname whose data is be updated") editUserRequestDto: EditUserRequestDto,
    ): ResponseEntity<EditUserResponseDto> {
        return ResponseEntity.ok(authServiceImpl.editUser(editUserRequestDto))
    }

    @Operation(
        summary = "Delete user account",
        description = "Deletes the user's account permanently. Requires user authentication.",
        responses = [
            ApiResponse(responseCode = "200", description = "User account deleted successfully"),
        ]
    )
    @DeleteMapping("/delete")
    @SecurityRequirement(name = SwaggerConfig.BEARER_AUTH)
    fun deleteUser(
        @AuthenticationPrincipal @Parameter(hidden = true) user: OAuth2User,
        @Valid @RequestBody @Parameter(description = "Delete user account") deleteUser: DeleteUserRequestDto
    ): ResponseEntity<DeleteUserResponseDto> {
        return ResponseEntity.ok(authServiceImpl.deleteUser(deleteUser))
    }

    @Operation(
        summary = "Logout user",
        description = "Logs out the user by invalidating their current session token. Requires user authentication.",
        responses = [
            ApiResponse(responseCode = "200", description = "User logged out successfully"),
        ]
    )
    @PostMapping("/logout")
    @SecurityRequirement(name = SwaggerConfig.BEARER_AUTH)
    fun logout(
        @AuthenticationPrincipal @Parameter(hidden = true) user: OAuth2User,
        @Valid @RequestBody @Parameter(description = "User nickname") nickname: String,
    ): ResponseEntity<Boolean> {
        return ResponseEntity.ok(authServiceImpl.logout(nickname))
    }
}
