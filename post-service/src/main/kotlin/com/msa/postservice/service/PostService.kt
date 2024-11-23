package com.msa.postservice.service

import com.msa.postservice.dto.PostDto
import com.msa.postservice.entity.Post

interface PostService {

    fun createPost(postDto: PostDto): PostDto

    fun getPostByPostId(postId: String): PostDto

    fun getPostsByUserId(userId: String): List<PostDto>

}