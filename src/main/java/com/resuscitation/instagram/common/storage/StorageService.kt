package com.resuscitation.instagram.common.storage

import org.springframework.web.multipart.MultipartFile

interface StorageService {
    /**
     * Upload file to storage
     * @param file file to upload
     * @return url of uploaded file
     */
    fun upload(file: MultipartFile): String
}
