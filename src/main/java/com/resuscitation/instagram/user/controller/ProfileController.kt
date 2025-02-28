package com.resuscitation.instagram.user.controller

import com.resuscitation.instagram.config.SwaggerConfig
import com.resuscitation.instagram.user.dto.ProfileDto
import com.resuscitation.instagram.user.dto.UpdateProfileDto
import com.resuscitation.instagram.user.service.ProfileServiceImpl
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v2/profile")
@Tag(name = "User Info", description = "User Info APIs")
@SecurityRequirement(name = SwaggerConfig.BEARER_AUTH)
class ProfileController(
    private val profileService: ProfileServiceImpl,
) {
    @Operation(
        summary = "View user profile",
        description = "Fetches the profile details of a user by their nickname.",
        responses = [
            ApiResponse(responseCode = "200", description = "Profile retrieved successfully"),
            ApiResponse(responseCode = "404", description = "Profile not found"),
        ],
    )
    @GetMapping("/{nickname}")
    fun showProfile(
        @PathVariable nickname: String,
    ): ResponseEntity<ProfileDto> {
        return ResponseEntity.ok(profileService.showProfile(nickname))
    }

    @Operation(
        summary = "Update user profile",
        description = "Updates the profile of a user using their nickname.",
        responses = [
            ApiResponse(responseCode = "200", description = "Profile updated successfully"),
            ApiResponse(responseCode = "400", description = "Bad request"),
            ApiResponse(responseCode = "404", description = "Profile not found"),
        ],
    )
    @PatchMapping("/update/{nickname}")
    fun updateProfile(
        @AuthenticationPrincipal @Parameter(hidden = true) user: OAuth2User,
        @PathVariable nickname: String,
        @Valid @RequestBody updateProfileDto: UpdateProfileDto,
    ): ResponseEntity<Boolean> {
        return ResponseEntity.ok(profileService.updateProfile(nickname, updateProfileDto))
    }
}
