package com.msa.userservice.vo

data class LoginRequest(
    var email: String = "",
    var password: String = "",
)