package com.resuscitation.instagram.security.auth.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val uuid: Long = 0L,
    @Column(unique = true)
    val nickname: String,
    val password: String = "",
    val email: String = "",
    @Column(unique = true)
    val phoneNumber: String = "",
    @Enumerated(EnumType.STRING)
    val userRole: UserRole = UserRole.ROLE_BASIC,
)
