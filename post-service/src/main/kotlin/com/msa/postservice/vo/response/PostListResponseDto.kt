package com.msa.postservice.vo.response

import java.time.LocalDateTime

data class PostListResponseDto (
    val userId: String,
    val username: String,
    val title: String,
    val postId: String,
    val updatedAt: LocalDateTime
)