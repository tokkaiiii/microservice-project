package com.msa.postservice.client

import com.msa.postservice.vo.response.UserOneResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "user-service")
interface UserServiceClient {

    @GetMapping("/users/{userId}")
    fun getUser(@PathVariable userId: String): UserOneResponse


}