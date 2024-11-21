package com.msa.userservice.exception

data class ErrorResponse (
    val statusCode: Int,
    val errorMessage: String
)