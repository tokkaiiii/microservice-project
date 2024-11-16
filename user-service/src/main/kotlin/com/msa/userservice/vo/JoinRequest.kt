package com.msa.userservice.vo

data class JoinRequest(
    var email: String,
    var username: String,
    var password: String,
)