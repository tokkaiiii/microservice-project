package com.msa.userservice.dto

data class UserDto(
    var email: String,
    var username: String,
    var password: String? = null,
    var encryptedPassword: String? = null,
    var userId: String? =null,
)