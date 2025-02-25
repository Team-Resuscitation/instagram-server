package com.resuscitation.instagram.security.auth.service

interface ValidationService {
    fun duplicationCheck(data: String): Boolean

    fun formatCheck(data: String): Boolean
}
