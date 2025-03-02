package com.resuscitation.instagram.user.service.data

import com.resuscitation.instagram.user.domain.User
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "User profile response object")
data class ProfileData(
    @field:Schema(description = "User's ID", example = "123456")
    val id: Long,
    @field:Schema(description = "The nickname of the user", example = "user123")
    val nickname: String,
    @field:Schema(description = "Introduction or bio of the user", example = "Hello! I love coding and sharing knowledge.")
    val bio: String?,
    @field:Schema(description = "URL to the user's profile image", example = "https://example.com/images/user123.jpg")
    val profileImage: String,
    @field:Schema(description = "The number of posts the user has made", example = "10")
    val followersCount: Int,
    @field:Schema(description = "The number of followers the user has", example = "100")
    val followingCount: Int,
) {
    companion object {
        fun from(
            user: User,
        ): ProfileData {
            return ProfileData(
                id = user.id,
                nickname = user.nickname,
                bio = user.bio,
                profileImage = user.profileImage ?: "https://picsum.photos/id/14/500/500",
                followersCount = user.followersCount,
                followingCount = user.followingsCount,
            )
        }
    }
}
