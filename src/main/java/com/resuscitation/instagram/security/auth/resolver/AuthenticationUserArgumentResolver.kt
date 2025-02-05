package com.resuscitation.instagram.security.auth.resolver

import com.resuscitation.instagram.security.auth.dto.AuthenticatedUserDto
import org.springframework.core.MethodParameter
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

class AuthenticationUserArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.getParameterAnnotation(AuthenticationPrincipal::class.java) != null &&
                AuthenticatedUserDto::class.java.isAssignableFrom(
                    parameter.parameterType
                )
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): Any? {
        val authentication = SecurityContextHolder.getContext().authentication
        val isNullable = parameter.isOptional

        return if (authentication != null && authentication.principal is AuthenticatedUserDto) {
            authentication.principal as AuthenticatedUserDto
        } else {
            if (isNullable) {
                null
            } else {
                throw IllegalArgumentException("Authentication principal is missing or invalid")
            }
        }
    }
}
