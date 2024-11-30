package com.msa.postservice.vo.request

data class PostRequestDto(
    val content: String,
    val title: String,
    var userId: String
)