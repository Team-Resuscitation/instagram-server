package com.resuscitation.instagram.user.domain

import jakarta.persistence.*
import java.sql.Timestamp

@Entity
class Profile(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val uuid: Long? = null,
    @Column(unique = true)
    val nickname: String,
    val name: String = "",
    val introduce: String = "",
    val profileImage: String = "",
    val following: Int = 0,
    val follower: Int = 0,
    val lastAccess: Timestamp = Timestamp(System.currentTimeMillis()),
)