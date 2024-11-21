package com.msa.userservice.service

import com.msa.userservice.dto.UserDto
import com.msa.userservice.exception.DuplicateEntityException
import com.msa.userservice.exception.UserNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Transactional
@SpringBootTest
class UserServiceTest(
    @Autowired val userService: UserService
) {

    @DisplayName("회원가입")
    @Test
    fun createUser() {
        val userDto1 = UserDto(
            email = "test1@mail.com",
            username = "test",
            password = "test",
            userId = UUID.randomUUID().toString()
        )
        val userDto2 = UserDto(
            email = "test2@mail.com",
            username = "test",
            password = "test",
            userId = UUID.randomUUID().toString()
        )
        val createUser1 = userService.createUser(userDto1)
        val createUser2 = userService.createUser(userDto2)
        val findUser1 = userService.getUserByEmail(createUser1.email)
        val findUser2 = userService.getUserByEmail(createUser2.email)
        testUser(createUser1,findUser1)
        testUser(createUser2,findUser2)
    }

    @DisplayName("중복 가입")
    @Test
    fun duplicateUser() {
        val userDto1 = UserDto(
            email = "test@mail.com",
            username = "test",
            password = "test",
            userId = UUID.randomUUID().toString()
        )
        val userDto2 = UserDto(
            email = "test@mail.com",
            username = "test",
            password = "test",
            userId = UUID.randomUUID().toString()
        )
        userService.createUser(userDto1)
        val exception =
            assertThrows<DuplicateEntityException> { userService.createUser(userDto2) }
        assertThat(exception.message).isEqualTo("user already exists")
    }

    @DisplayName("회원단건 조회 by email")
    @Test
    fun getUserByEmail() {
        val userDto = UserDto(
            email = "test@mail.com",
            username = "test",
            password = "test",
            userId = UUID.randomUUID().toString()
        )
        val createUser = userService.createUser(userDto)
        val findUser = userService.getUserByEmail(createUser.email)
        testUser(findUser, createUser)
    }

    @DisplayName("회원단건 조회 by userId")
    @Test
    fun getUserByUserId() {
        val userDto = UserDto(
            email = "test@mail.com",
            username = "test",
            password = "test",
            userId = UUID.randomUUID().toString()
        )
        val createUser = userService.createUser(userDto)
        val findUser = userService.getUserByUserId(createUser.userId!!)
        testUser(findUser, createUser)
    }

    @DisplayName("로그인")
    @Test
    fun loadUserByUsername() {
        val userDto = UserDto(
            email = "test@mail.com",
            username = "test",
            password = "test",
            userId = UUID.randomUUID().toString()
        )
        val createUser = userService.createUser(userDto)
        val loginUser = userService.loadUserByUsername(createUser.email)
        assertThat(loginUser.username).isEqualTo(createUser.email)
        assertThat(loginUser.password).isEqualTo(createUser.encryptedPassword)
    }

    @DisplayName("잘못된 이메일 로그인")
    @Test
    fun failLogin() {
        val userDto = UserDto(
            email = "test@mail.com",
            username = "test",
            password = "test",
            userId = UUID.randomUUID().toString()
        )
        val createUser = userService.createUser(userDto)
        val exception = assertThrows<UserNotFoundException> {
            userService.loadUserByUsername("${createUser.email}1")
        }
        assertThat(exception.message).isEqualTo("User not found")
    }

    private fun testUser(
        findUser: UserDto,
        createUser: UserDto
    ) {
        assertThat(findUser.email).isEqualTo(createUser.email)
        assertThat(findUser.userId).isEqualTo(createUser.userId)
        assertThat(findUser.username).isEqualTo(createUser.username)
    }
}