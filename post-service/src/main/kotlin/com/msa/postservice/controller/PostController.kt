package com.msa.postservice.controller

import com.msa.postservice.dto.PostDto
import com.msa.postservice.messagequeue.KafkaProducer
import com.msa.postservice.messagequeue.PostProducer
import com.msa.postservice.service.PostService
import com.msa.postservice.vo.response.PostOneResponseDto
import com.msa.postservice.vo.request.PostRequestDto
import org.springframework.core.env.Environment
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

private const val TOPIC_POST = "topic_post"

@RestController
class PostController(
    private val env: Environment,
    private val postService: PostService,
    private val kafkaProducer: KafkaProducer,
    private val postProducer: PostProducer
) {

    @GetMapping("/actuator/health_check")
    fun healthCheck(): String {
        return "{\"status\":\"UP\" \n" +
                "\"token-key\":\"${env.getProperty("jwt.token.secrete")}\" \n" +
                "\"token-time\":\"${env.getProperty("jwt.token.expiration_time")}\"}"

    }

    @PostMapping("/posts")
    fun createPost(@RequestBody postRequestDto: PostRequestDto): ResponseEntity<PostDto> {
        val createPost = postService.createPost(postRequestDto)
//        kafkaProducer.send("post_topic",postDto)
//        postProducer.send(TOPIC_POST, postDto)
        return ResponseEntity.ok(createPost)
    }

    @GetMapping("/posts/{postId}")
    fun getPost(@PathVariable("postId") postId: String): ResponseEntity<PostOneResponseDto> {
        val findPost = postService.getPostByPostId(postId)
        val responseDto = PostDto.toPostOneResponseDto(findPost)
        return ResponseEntity.ok(responseDto)
    }

    @GetMapping("/posts/")
    fun getPosts():
            List<PostDto> {
        return listOf()
    }
}