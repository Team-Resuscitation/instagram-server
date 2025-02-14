package com.resuscitation.instagram.security.auth.domain

import jakarta.persistence.*

@Entity
class SocialUser(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val uuid: Long? = null,

    val provider: SocialType = SocialType.GOOGLE,

    @Column(unique = true)
    val providerId: String,
)