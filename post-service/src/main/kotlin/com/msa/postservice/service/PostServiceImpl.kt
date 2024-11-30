package com.msa.postservice.service

import com.msa.postservice.client.UserServiceClient
import com.msa.postservice.dto.PostDto
import com.msa.postservice.entity.Post
import com.msa.postservice.repository.PostRepository
import com.msa.postservice.vo.request.PostRequestDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import java.util.stream.Collectors

@Service
@Transactional(readOnly = true)
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val userServiceClient: UserServiceClient
) : PostService {

    @Transactional
    override fun createPost(postRequestDto: PostRequestDto): PostDto {
        val userId = postRequestDto.userId
        val username = userServiceClient.getUser(userId).username
        val postDto = PostDto.toPostDto(postRequestDto,username)
        val post = Post.toPost(postDto)
        postRepository.save(post)
        return postDto.also { it.id = post.id }
    }

    override fun getPostByPostId(postId: String): PostDto {
        val findPost = postRepository.findByPostId(postId)
        return PostDto.toPostDto(findPost)
    }

    override fun getPostsByUserId(userId: String): List<PostDto> {
        val findPosts = postRepository.findByUserId(userId)
        return findPosts.stream()
            .map { PostDto.toPostDto(it) }
            .collect(Collectors.toList())
    }
}