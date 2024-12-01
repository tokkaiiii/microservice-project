package com.msa.postservice.dto

import com.msa.postservice.entity.Post
import com.msa.postservice.vo.response.PostOneResponseDto
import com.msa.postservice.vo.request.PostRequestDto
import java.time.LocalDateTime
import java.util.UUID

data class PostDto(
    var id: Long? = null,
    var content: String,
    var title: String,
    var userId: String,
    var username: String?,
    var postId: String,
    var createdAt: LocalDateTime? = null,
    var updatedAt: LocalDateTime? = null,
) {
    companion object {

        fun toPostDto(postRequestDto: PostRequestDto,username: String?) = PostDto(
            content = postRequestDto.content,
            title = postRequestDto.title,
            userId = postRequestDto.userId,
            username = username,
            postId = UUID.randomUUID().toString()
        )

        fun toPostDto(post: Post) = PostDto(
            content = post.content,
            title = post.title,
            userId = post.userId,
            username = post.username,
            postId = post.postId,
            createdAt = post.createdAt,
            updatedAt = post.updatedAt
        )

        fun toPostOneResponseDto(postDto: PostDto) = PostOneResponseDto(
            postId = postDto.postId,
            title = postDto.title,
            content = postDto.content,
            userId = postDto.userId,
            username = postDto.username,
        )
    }

}
