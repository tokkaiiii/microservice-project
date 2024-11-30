package com.msa.postservice.service

import com.msa.postservice.dto.PostDto
import com.msa.postservice.vo.request.PostRequestDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.util.*

@SpringBootTest
@Transactional
class PostServiceImplTest(
    @Autowired private val postService: PostService
) {

    @DisplayName("게시물 작성")
    @Test
    fun createPost() {
        val postDto = PostRequestDto(
            content = "Hello world!",
            title = "Hello world!",
            userId = "9321229c-e82e-498b-849d-a9b2191c9311",
        )
        val createPost = postService.createPost(postDto)
        assertThat(createPost).isNotNull
        assertThat(createPost.id).isNotNull()
        assertThat(createPost.title).isEqualTo(postDto.title)
        assertThat(createPost.content).isEqualTo(postDto.content)
        assertThat(createPost.userId).isEqualTo(postDto.userId)
    }

    @DisplayName("게시물 단건 조회")
    @Test
    fun getPostByPostId(){
        val postDto = PostRequestDto(
            content = "Hello world!",
            title = "Hello world!",
            userId = "9321229c-e82e-498b-849d-a9b2191c9311",
        )
        val createPost = postService.createPost(postDto)
        val postId = createPost.postId
        val findPost = postService.getPostByPostId(postId!!)
        assertThat(findPost).isNotNull
        assertThat(findPost.postId).isEqualTo(createPost.postId)
        assertThat(findPost.title).isEqualTo(postDto.title)
        assertThat(findPost.userId).isEqualTo(postDto.userId)
    }

    @DisplayName("나의 게시물 리스트 조회")
    @Test
    fun getPostsByUserId(){

        val postDto1 = PostRequestDto(
            content = "Hello world1!",
            title = "Hello world1!",
            userId = "9321229c-e82e-498b-849d-a9b2191c9311",
        )
        val postDto2 = PostRequestDto(
            content = "Hello world2!",
            title = "Hello worl2!",
            userId = "9321229c-e82e-498b-849d-a9b2191c9311",
        )
        postService.createPost(postDto1)
        postService.createPost(postDto2)
        val posts = postService.getPostsByUserId("9321229c-e82e-498b-849d-a9b2191c9311")
        assertThat(posts).hasSize(2)
        assertThat(posts).extracting("title").contains(postDto1.title)
    }
}