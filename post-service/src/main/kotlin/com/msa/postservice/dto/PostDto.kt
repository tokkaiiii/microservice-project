package com.msa.postservice.dto

data class PostDto (
    var content: String,
    var title: String,
    var userId: String,
    var username: String,
    var postId: String? = null,
)