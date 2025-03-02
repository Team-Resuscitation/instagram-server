package com.resuscitation.instagram.common.storage

import com.resuscitation.instagram.common.storage.properties.ImageBBProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Profile
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestClient
import org.springframework.web.multipart.MultipartFile
import java.util.Base64

@Profile("!test")
@Component
@EnableConfigurationProperties(ImageBBProperties::class)
class ImageBBService(
    private val imageBBProperties: ImageBBProperties,
) : StorageService {
    private val restClient: RestClient = RestClient.create()

    override fun upload(file: MultipartFile): String {
        // Multipart/form-data 형식으로 이미지를 Base64로 인코딩하여 전송
        val parts: MultiValueMap<String, Any> = LinkedMultiValueMap()
        parts.add("image", Base64.getEncoder().encodeToString(file.bytes))

        val response: Map<*, *>? =
            restClient.post()
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
