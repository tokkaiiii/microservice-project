package com.msa.postservice.entity

import com.msa.postservice.dto.PostDto
import com.msa.userservice.audit.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import java.io.Serializable

@Entity
class Post(
    @Column(unique = true, nullable = false)
    var postId: String = "",
    @Column(nullable = false)
    var title: String = "",
    @Column(nullable = false)
    var content: String = "",
    @Column(nullable = false)
    var userId: String = "",
    @Column(nullable = false)
    var username: String = ""
) : BaseEntity(), Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    var id: Long? = null

    companion object {
        fun toPost(postDto: PostDto) = Post(
            postId = postDto.postId,
            title = postDto.title,
            content = postDto.content,
            userId = postDto.userId,
            username = postDto.username
        )
    }

}