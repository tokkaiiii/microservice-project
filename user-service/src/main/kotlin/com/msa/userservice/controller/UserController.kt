package com.msa.userservice.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user-service")
class UserController {

    @GetMapping("/users")
    fun health (): String {
        return "Users found"
    }

    @GetMapping("/check")
    fun check(): String {
        return "Hi, there. This is a message from User service"
    }

}