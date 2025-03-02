package com.resuscitation.instagram.user.domain

import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.SoftDelete

@Entity
@Table(name = "users")
@SoftDelete(columnName = "is_deleted")
class User(
    /** 사용자 ID */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    /** 사용자 닉네임 */
    @Column(unique = true)
    val nickname: String,

    /** 사용자 이메일 */
    @Column(unique = true)
    val email: String,

    /** 사용자 전화번호 */
    @Column(unique = true)
    val phoneNumber: String? = null,

    /** 사용자 비밀번호 */
    var password: String? = null,

    /** OAuth Provider */
    @ElementCollection(fetch = FetchType.LAZY)
    val providers: Set<Provider> = emptySet(),

    /** 사용자 역할 */
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    val roles: Set<UserRole> = setOf(UserRole.USER),


    /** 사용자 프로필 이미지 */
    var profileImage: String? = null,

    /** 사용자 소개 */
    val bio: String? = null,

    /** 팔로워 */
    @ElementCollection(fetch = FetchType.LAZY)
    val follwers: Set<String> = emptySet(),
    var followersCount: Int = 0,

    /** 팔로잉 */
    @ElementCollection(fetch = FetchType.LAZY)
    val followings: Set<String> = emptySet(),
    var followingsCount: Int = 0,
)
