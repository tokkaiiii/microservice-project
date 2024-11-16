package com.msa.userservice.service

import com.msa.userservice.dto.UserDto
import com.msa.userservice.entity.user.User
import com.msa.userservice.exception.UserNotFoundException
import com.msa.userservice.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService {

    @Transactional
    override fun createUser(userDto: UserDto): UserDto {
        val encryptedPassword = passwordEncoder.encode(userDto.password)
        val user = User(
            email = userDto.email,
            password = encryptedPassword,
            userId = UUID.randomUUID().toString(),
            username = userDto.username
        )
        userRepository.save(user)
        return userDto.apply { userId = user.userId }
    }

    override fun getUserByEmail(email: String): UserDto {
        val user = userRepository.findByEmail(email)
            ?: throw UserNotFoundException("User with email $email not found")
        return UserDto(
            email = user.email,
            userId = user.userId,
            username = user.username,
            encryptedPassword = user.password
        )
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        if (username == null) throw UsernameNotFoundException("User $username not found")
        val user = userRepository.findByEmail(username)
            ?: throw UserNotFoundException("User $username not found")
        return org.springframework.security.core.userdetails.User(
            user.email,
            user.password,
            true,true,true,true,
            mutableListOf()
        )
    }


}