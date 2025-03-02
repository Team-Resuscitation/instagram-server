package com.resuscitation.instagram.auth.handler

import com.resuscitation.instagram.auth.TokenProvider
import com.resuscitation.instagram.user.domain.User
import com.resuscitation.instagram.user.repository.UserRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class OAuth2SuccessHandler(
    private val userRepository: UserRepository,
    private val tokenProvider: TokenProvider,
) : SimpleUrlAuthenticationSuccessHandler() {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication,
    ) {
        val oAuth2User = authentication.principal as OAuth2User
        val identifier = oAuth2User.name

        val user: User =
            userRepository.findByIdOrNull(identifier.toLong())
                ?: throw IllegalArgumentException("User not found with identifier: $identifier")

        val token = tokenProvider.encode(user)

        val redirectUrl = "${request.scheme}://${request.serverName}:${request.serverPort}/?token=$token"

        redirectStrategy.sendRedirect(request, response, redirectUrl)
    }
}
