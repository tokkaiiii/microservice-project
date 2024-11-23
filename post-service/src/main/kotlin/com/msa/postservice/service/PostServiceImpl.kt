package com.msa.postservice.service

import com.msa.postservice.dto.PostDto
import com.msa.postservice.entity.Post
import com.msa.postservice.repository.PostRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import java.util.stream.Collectors

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
            username = postDto.username
        )
        postRepository.save(post)
        return postDto.also { it.postId = post.postId }
    }

    override fun getPostByPostId(postId: String): PostDto {
        val findPost = postRepository.findByPostId(postId)
        return PostDto(
            postId = findPost.postId,
            title = findPost.title,
            content = findPost.content,
            userId = findPost.userId,
            username = findPost.username
        )
    }

    override fun getPostsByUserId(userId: String): List<PostDto> {
        val findPosts = postRepository.findByUserId(userId)
        return findPosts.stream().map { p ->
            PostDto(p.content, p.title, p.userId, p.username).toPostDto(p)
        }.collect(Collectors.toList())
    }
}