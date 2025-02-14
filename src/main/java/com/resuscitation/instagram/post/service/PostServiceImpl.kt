package com.resuscitation.instagram.post.service

import com.resuscitation.instagram.post.domain.Media
import com.resuscitation.instagram.post.domain.MediaType
import com.resuscitation.instagram.post.domain.Post
import com.resuscitation.instagram.post.dto.PostRequestDto
import com.resuscitation.instagram.post.dto.PostResponseDto
import com.resuscitation.instagram.post.repository.MediaRepository
import com.resuscitation.instagram.post.repository.PostRepository
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val imageBBService: ImageBBService,
    private val mediaRepository: MediaRepository,
) : PostService {
    override fun uploadPost(postRequestDto: PostRequestDto): Boolean {
        val post = postRepository.save(
            Post(
                author = postRequestDto.author,
                imageCnt = postRequestDto.medias.count(),
                content = postRequestDto.content,
                timestamp = Date.from(Instant.now()),
            )
        )
        postRequestDto.medias.forEach { media ->
            mediaRepository.save(
                Media(
                    postId = post.idx,
                    link = imageBBService.uploadImage(media),
                    contentType = MediaType.MEDIATYPE_IMAGE,
                )
            )
        }
        return true
    }

    override fun readPost(postId: String): PostResponseDto {
        val post = postRepository.findByIdx(postId.toLong())
        val medias = mutableListOf<String>()
        mediaRepository.findByPostId(postId.toLong()).forEach { media ->
            medias.add(media.link)
        }
        return PostResponseDto(
            author = post.author,
            medias = medias.toList(),
            content = post.content,
            timestamp = post.timestamp,
            likes = post.likes,
            comments = post.comments,
        )
    }

    override fun updatePost(postId: String, postRequestDto: PostRequestDto): Boolean {
        val post = postRepository.findByIdx(postId.toLong())
        postRepository.save(
            Post(
                author = post.author,
                imageCnt = post.imageCnt,
                content = postRequestDto.content ?: post.content,
                timestamp = post.timestamp,
                likes = post.likes,
                comments = post.comments,
            )
        )
        return true
    }

    override fun deletePost(postId: String): Boolean {
        postRepository.deleteByIdx(postId.toLong())
        return true
    }

    override fun timeline(nickname: String): List<PostResponseDto> {
        // TODO
        return listOf(
            PostResponseDto(
                author = "",
                medias = listOf(),
                content = "",
                timestamp = Date.from(Instant.now()),
                likes = 0,
                comments = 0,
            )
        )
    }
}