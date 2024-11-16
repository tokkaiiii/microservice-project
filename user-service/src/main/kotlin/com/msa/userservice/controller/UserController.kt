package com.msa.userservice.controller

import com.msa.userservice.dto.UserDto
import com.msa.userservice.service.UserService
import com.msa.userservice.vo.JoinRequest
import com.msa.userservice.vo.JoinResponse
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    @PostMapping
    fun createUser(@RequestBody joinRequest: JoinRequest): ResponseEntity<JoinResponse>{
        val userDto = UserDto(
            email = joinRequest.email,
            username = joinRequest.username,
            password = joinRequest.password,
            encryptedPassword = ""
        ).also { userService.createUser(it) }
        val joinResponse = JoinResponse(
            email = userDto.email,
            username = userDto.username,
            userId = userDto.userId!!
        )
        return ResponseEntity.status(CREATED).body(joinResponse)
    }

}