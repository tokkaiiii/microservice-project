package com.msa.postservice.service

import com.msa.postservice.dto.PostDto
import com.msa.postservice.entity.Post
import com.msa.postservice.vo.request.PostRequestDto

interface PostService {

    fun createPost(postRequestDto: PostRequestDto): PostDto

    fun getPostByPostId(postId: String): PostDto

    fun getPostsByUserId(userId: String): List<PostDto>

}