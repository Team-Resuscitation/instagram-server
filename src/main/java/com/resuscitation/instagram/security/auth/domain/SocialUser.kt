package com.resuscitation.instagram.security.auth.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class SocialUser(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val uuid: Long? = null,
    val provider: SocialType = SocialType.GOOGLE,
    @Column(unique = true)
    val providerId: String,
)
