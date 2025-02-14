package com.resuscitation.instagram.security.auth.service

interface PasswordService {
    fun encodePassword(password: String): String

    fun verifyPassword(rawPassword: String, encodedPassword: String): Boolean
}