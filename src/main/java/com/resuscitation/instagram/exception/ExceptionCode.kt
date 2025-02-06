package com.resuscitation.instagram.exception

import org.springframework.http.HttpStatus

enum class ExceptionCode(val status: HttpStatus, val message: String) {
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "Nickname already exists"),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "Email already exists"),
    DUPLICATE_PHONENUMBER(HttpStatus.CONFLICT, "Phone number already exists"),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "Invalid request data"),
    UNMATCHED_PASSWORD(HttpStatus.BAD_REQUEST, "Password does not match"),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "Unauthorized access"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error")
}
