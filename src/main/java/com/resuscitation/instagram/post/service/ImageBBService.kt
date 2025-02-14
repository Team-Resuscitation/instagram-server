package com.resuscitation.instagram.post.service

import com.fasterxml.jackson.databind.JsonNode
import com.resuscitation.instagram.post.configuration.ImageBBProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Component
@EnableConfigurationProperties(ImageBBProperties::class)
class ImageBBService(
    private val imageBBProperties: ImageBBProperties,
) {
    fun uploadImage(file: MultipartFile): String {
        val encodedFile = Base64.getEncoder().encodeToString(file.bytes)
        val formData = mapOf(
            "key" to imageBBProperties.secretKey,
            "image" to encodedFile
        )

        val response = RestClient
            .create()
            .post()
            .uri(imageBBProperties.uploadUrl)
            .body(formData)
            .retrieve()
            .toEntity(JsonNode::class.java)
            .body ?: throw IllegalStateException("Failed to upload image")

        if (response == null || response.path("data").path("url").asText().isBlank()) {
            throw IllegalStateException("Failed to upload file to ImageBB")
        }

        return response.path("data").path("url").asText()
    }
}
