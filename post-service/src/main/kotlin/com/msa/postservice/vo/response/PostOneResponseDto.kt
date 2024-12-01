package com.msa.postservice.vo.response

data class PostOneResponseDto(
    val postId: String,
    val title: String,
    val content: String,
    val userId: String,
    val username: String?,
)