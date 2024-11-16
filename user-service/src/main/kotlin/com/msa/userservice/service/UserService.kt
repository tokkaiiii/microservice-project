package com.msa.userservice.service

import com.msa.userservice.dto.UserDto
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService : UserDetailsService {

    fun createUser(userDto: UserDto): UserDto

    fun getUserByEmail(email: String): UserDto
}