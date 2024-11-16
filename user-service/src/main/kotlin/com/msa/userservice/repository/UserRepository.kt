package com.msa.userservice.repository

import com.msa.userservice.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>{
    fun findByEmail(email: String): User?

    fun findByUserId(userId: String): User?
}