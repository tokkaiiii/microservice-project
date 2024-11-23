package com.msa.postservice.exception

import com.msa.postservice.util.logger
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.io.IOException

@RestControllerAdvice
class GlobalExceptionHandler {
    @RestControllerAdvice
    class GlobalExceptionHandler {
        val log = logger()

        @ExceptionHandler(IOException::class)
        fun handleIOException(e: IOException): ResponseEntity<ErrorResponse> {
            log.error(LOG_ERROR_MESSAGE, e.message, e)

            val errorResponse = ErrorResponse(INTERNAL_SERVER_ERROR.value(), e.message!!)
            return ResponseEntity(errorResponse, resolve(errorResponse.statusCode)!!)
        }


    }

}

private const val LOG_ERROR_MESSAGE = "Error exception occurred: {}"
private const val LOG_WARN_MESSAGE = "Warning exception occurred: {}"
private const val LOG_INFO_MESSAGE = "Exception occurred: {}"
