package com.resuscitation.instagram.auth.controller

import com.resuscitation.instagram.auth.controller.data.LoginRequest
import com.resuscitation.instagram.auth.controller.data.LoginResponse
import com.resuscitation.instagram.auth.controller.data.RegisterRequest
import com.resuscitation.instagram.auth.service.AuthService
import com.resuscitation.instagram.config.SwaggerConfig.Companion.AUTH_API
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v2/auth")
@Tag(name = AUTH_API, description = "User Auth APIs")
class AuthController(
    private val authService: AuthService,
) {
    @Operation(
        summary = "User register api",
        description = "Sign up for Instagram using a username, email, and password.",
    )
    @PostMapping("/register")
    fun instagramRegister(
        @Valid @RequestBody @Parameter(description = "User registration request body") registerRequest: RegisterRequest,
    ): ResponseEntity<LoginResponse> {
        return ResponseEntity.ok(
            LoginResponse(authService.register(registerRequest)),
        )
    }

    @Operation(
        summary = "User login api",
        description = "Log in to Instagram using email and password.",
    )
    @PostMapping("/login")
    fun instagramLogin(
        @Valid @RequestBody @Parameter(description = "User login request body") loginRequest: LoginRequest,
    ): ResponseEntity<LoginResponse> {
        return ResponseEntity.ok(
            LoginResponse(authService.login(loginRequest)),
        )
    }
}
