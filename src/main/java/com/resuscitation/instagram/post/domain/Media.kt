package com.resuscitation.instagram.post.domain

import jakarta.persistence.*

@Entity
class Media (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    val postId: Long = 0L,

    val link: String = "",

    val contentType: MediaType = MediaType.MEDIATYPE_IMAGE,
)