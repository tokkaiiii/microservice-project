package com.msa.postservice.exception

data class ErrorResponse (
    val statusCode: Int,
    val errorMessage: String
)