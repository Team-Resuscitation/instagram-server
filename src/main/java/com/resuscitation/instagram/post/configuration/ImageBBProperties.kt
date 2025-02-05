package com.resuscitation.instagram.post.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "instagram.imgbb")
data class ImageBBProperties(
    val secretKey: String,
    val uploadUrl: String,
)