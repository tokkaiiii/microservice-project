package com.msa.postservice.controller

import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PostController(
    private val env: Environment
) {

    @GetMapping("/actuator/health_check")
    fun healthCheck(): String {
        return "{\"status\":\"UP\" \n" +
                "\"token-key\":\"${env.getProperty("jwt.token.secrete")}\" \n" +
                "\"token-time\":\"${env.getProperty("jwt.token.expiration_time")}\"}"

    }

    @GetMapping("/posts")
    fun getPosts(): String {
        return "hello"
    }

}