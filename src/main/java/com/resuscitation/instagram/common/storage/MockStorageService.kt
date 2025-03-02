package com.resuscitation.instagram.common.storage

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Profile("test")
@Component
class MockStorageService : StorageService {
    override fun upload(file: MultipartFile): String {
        return "mocked"
    }
}
