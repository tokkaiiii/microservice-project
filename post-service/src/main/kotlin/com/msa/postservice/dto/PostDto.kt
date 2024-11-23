package com.msa.postservice.dto

import com.msa.postservice.entity.Post

data class PostDto (
    var content: String,
    var title: String,
    var userId: String,
    var username: String,
    var postId: String? = null,
){
    fun toPostDto(post: Post) = PostDto(
        content = content,
        title = title,
        userId = userId,
        username = username,
        postId = post.postId
    )
}
