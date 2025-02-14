package com.resuscitation.instagram.security.filter

import com.resuscitation.instagram.security.jwt.JwtProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtFilter(
    private val jwtProvider: JwtProvider
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader(AUTHORIZATION)
        val jwt = if (header != null && header.startsWith("Bearer ")) {
            header.substring(7)
        } else {
            throw IllegalArgumentException("Invalid token format: missing 'Bearer ' prefix")
        }

        val authenticatedUser = jwtProvider.extractToken(jwt)
        try {
            val auth = jwtProvider.getAuthentication(authenticatedUser)
            SecurityContextHolder.getContext().authentication = auth
        } catch (ex: Exception) {
            SecurityContextHolder.clearContext()
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
            return
        }
        filterChain.doFilter(request, response)
    }
}