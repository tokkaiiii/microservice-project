package com.msa.postservice.service

import com.msa.postservice.dto.PostDto
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class PostServiceImplTest(
    @Autowired private val postService: PostService
) {

    @DisplayName("게시물 작성")
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

    @DisplayName("게시물 단건 조회")
    @Test
    fun getPostByPostId(){
        val postDto = PostDto(
            content = "Hello world!",
            title = "Hello world!",
            userId = "user1",
            username = "username1"
        )
        val createPost = postService.createPost(postDto)
        val postId = createPost.postId
        val findPost = postService.getPostByPostId(postId!!)
        assertThat(findPost).isNotNull
        assertThat(findPost.postId).isEqualTo(createPost.postId)
        assertThat(findPost.title).isEqualTo(postDto.title)
        assertThat(findPost.userId).isEqualTo(postDto.userId)
        assertThat(findPost.username).isEqualTo(postDto.username)
    }

    @DisplayName("나의 게시물 리스트 조회")
    @Test
    fun getPostsByUserId(){

        val postDto1 = PostDto(
            content = "Hello world1!",
            title = "Hello world1!",
            userId = "user1",
            username = "username1"
        )
        val postDto2 = PostDto(
            content = "Hello world2!",
            title = "Hello worl2!",
            userId = "user1",
            username = "username1"
        )
        postService.createPost(postDto1)
        postService.createPost(postDto2)
        val posts = postService.getPostsByUserId("user1")
        assertThat(posts).hasSize(2)
        assertThat(posts).extracting("title").contains(postDto1.title)
    }
}