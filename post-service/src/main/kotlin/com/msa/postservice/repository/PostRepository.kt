package com.msa.postservice.repository

import com.msa.postservice.entity.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post,Long> {
    fun findByPostId(postId: String): Post
    fun findByUserId(userId: String): List<Post>
}