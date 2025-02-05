package com.resuscitation.instagram.user.service

import com.resuscitation.instagram.post.dto.PostResponseDto
import com.resuscitation.instagram.post.repository.MediaRepository
import com.resuscitation.instagram.post.repository.PostRepository
import com.resuscitation.instagram.user.domain.Profile
import com.resuscitation.instagram.user.dto.ProfileDto
import com.resuscitation.instagram.user.dto.UpdateProfileDto
import com.resuscitation.instagram.user.repository.ProfileRepository
import org.springframework.stereotype.Service

@Service
class ProfileServiceImpl(
    val profileRepository: ProfileRepository,
    private val postRepository: PostRepository,
    private val mediaRepository: MediaRepository,
) : ProfileService {
    override fun showProfile(nickname: String): ProfileDto {
        val profile = profileRepository.findByNickname(nickname).orElseThrow{IllegalArgumentException("User not found.")}
        return ProfileDto(
            nickname = profile.nickname,
            name = profile.name,
            introduce = profile.introduce,
            profileImage = profile.profileImage,
            feed = feed(profile.nickname),
        )
    }

    override fun updateProfile(nickname: String, updateProfileDto: UpdateProfileDto): Boolean {
        val profile = profileRepository.findByNickname(nickname).orElseThrow{IllegalArgumentException("User not found.")}
        val updatedProfile = Profile(
            nickname = updateProfileDto.nickname?: profile.nickname,
            name = updateProfileDto.name?: profile.name,
            introduce = updateProfileDto.introduce?: profile.introduce,
            profileImage = updateProfileDto.profileImage?: profile.profileImage,
        )
        profileRepository.save(updatedProfile)
        return true
    }

    fun feed(nickname: String): List<PostResponseDto> {
        val posts = postRepository.findByAuthor(nickname).sortedBy { it.timestamp }
        return posts.map { post ->
            PostResponseDto(
                author = post.author,
                medias = mediaRepository.findByPostId(post.idx).map { it.link },
                content = post.content,
                timestamp = post.timestamp,
                likes = post.likes,
                comments = post.comments,
            )
        }
    }
}