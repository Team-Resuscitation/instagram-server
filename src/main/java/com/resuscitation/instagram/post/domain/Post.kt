package com.resuscitation.instagram.post.domain

import jakarta.persistence.*
import java.util.*

@Entity
class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idx: Long = 0L,
    val author: String,
    val imageCnt: Int,
    @Column(columnDefinition = "LONGTEXT")
    val content: String = "",
    val timestamp: Date,
    val likes: Int = 0,
    val comments: Int = 0,
)