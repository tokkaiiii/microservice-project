package com.msa.postservice.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PostController {

    @GetMapping("/posts")
    fun getPosts(): String {
        return "hello"
    }

}