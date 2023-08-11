package com.resuscitation.Instagram.user.entity

import jakarta.persistence.*

@Entity
public class UserEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var idx: Long? = null,

        var nickname: String = "",

        @Column(unique = true)
        var email: String = "",

        var password: String = "",

        var name: String = "",

        var introduce: String = "",

        @Column(unique = true)
        var oauthToken: String? = null,

        @ElementCollection(fetch = FetchType.EAGER)
        var roles: MutableList<UserRole> = mutableListOf()


) {
    override fun toString(): String {
        return "UserEntity(idx=$idx, nickname='$nickname', email='$email', password='$password', name='$name', introduce='$introduce', oauthToken=$oauthToken)"
    }
}