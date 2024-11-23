package com.msa.postservice.service

import com.msa.postservice.dto.PostDto
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PostServiceImplTest(
    @Autowired private val postService: PostService
) {

    @Test
    fun createPost() {
        val postDto = PostDto(
            content = "Hello world!",
            title = "Hello world!",
            userId = "user1",
            username = "username1"
        )
        val createPost = postService.createPost(postDto)
        assertThat(createPost).isNotNull
        assertThat(createPost.title).isEqualTo(postDto.title)
        assertThat(createPost.content).isEqualTo(postDto.content)
        assertThat(createPost.userId).isEqualTo(postDto.userId)
        assertThat(createPost.username).isEqualTo(postDto.username)
    }
}