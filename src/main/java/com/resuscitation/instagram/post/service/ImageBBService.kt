package com.resuscitation.instagram.post.service

import com.resuscitation.instagram.post.configuration.ImageBBProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.multipart.MultipartFile
import java.util.*
import org.springframework.http.MediaType
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

@Component
@EnableConfigurationProperties(ImageBBProperties::class)
class ImageBBService(
    private val imageBBProperties: ImageBBProperties,
) {
    private val restClient: RestClient = RestClient.create()

    fun uploadImage(image: MultipartFile): String {
        // Multipart/form-data 형식으로 이미지를 Base64로 인코딩하여 전송
        val parts: MultiValueMap<String, Any> = LinkedMultiValueMap()
        parts.add("image", Base64.getEncoder().encodeToString(image.bytes))


        val response: Map<*, *>? = restClient.post()
            .uri("$IMGBB_API_URL?key=${imageBBProperties.apiKey}")
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(parts)
            .retrieve()
            .body(Map::class.java)

        // 업로드 결과에서 이미지 URL 추출
        val data = response?.get("data") as Map<*, *>?
        return data?.get("url").toString()
    }

    companion object {
        const val IMGBB_API_URL = "https://api.imgbb.com/1/upload"
    }
}
