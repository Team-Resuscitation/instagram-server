package com.resuscitation.instagram.exception

open class Exception(exceptionCode: ExceptionCode) : RuntimeException(exceptionCode.message)