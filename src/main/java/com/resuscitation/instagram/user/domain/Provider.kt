package com.resuscitation.instagram.user.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Embeddable
data class Provider(
    @Column(name = "provider_type")
    @Enumerated(EnumType.STRING)
    val provider: ProviderType,
    @Column(name = "provider_id")
    val providerId: String,
)
