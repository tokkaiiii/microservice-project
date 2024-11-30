package com.msa.postservice.dto.kafka

data class Payload(
    val id: Long,
    val createdAt: Long,
    val updatedAt: Long,
    val content: String,
    val postId: String,
    val title: String,
    val userId: String,
    val username: String
)
