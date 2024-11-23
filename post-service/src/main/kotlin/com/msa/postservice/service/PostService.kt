package com.msa.postservice.service

import com.msa.postservice.dto.PostDto

interface PostService {

    fun createPost(postDto: PostDto): PostDto

}