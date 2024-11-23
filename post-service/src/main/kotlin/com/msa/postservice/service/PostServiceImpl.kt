package com.msa.postservice.service

import com.msa.postservice.dto.PostDto
import com.msa.postservice.entity.Post
import com.msa.postservice.repository.PostRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class PostServiceImpl(
    private val postRepository: PostRepository,
) : PostService {

    @Transactional
    override fun createPost(postDto: PostDto): PostDto {
        val post = Post(
            postId = UUID.randomUUID().toString(),
            title = postDto.title,
            content = postDto.content,
            userId = postDto.userId,
            userame = postDto.username
        )
        postRepository.save(post)
        return postDto.also { it.postId = post.postId }
    }
}