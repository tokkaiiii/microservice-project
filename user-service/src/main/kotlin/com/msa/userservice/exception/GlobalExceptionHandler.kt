package com.msa.userservice.exception

import com.msa.userservice.util.logger
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.io.IOException

@RestControllerAdvice
class GlobalExceptionHandler {
    val log = logger()

    @ExceptionHandler(IOException::class)
    fun handleIOException(e: IOException): ResponseEntity<ErrorResponse> {
        log.error(LOG_ERROR_MESSAGE, e.message, e)

        val errorResponse = ErrorResponse(INTERNAL_SERVER_ERROR.value(),e.message!!)
        return ResponseEntity(errorResponse, resolve(errorResponse.statusCode)!!)
    }

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(ex: UserNotFoundException): ResponseEntity<String> {

        return ResponseEntity.status(NOT_FOUND).body(ex.message)
    }

    @ExceptionHandler(DuplicateEntityException::class)
    fun handleDuplicateEntityException(e: DuplicateEntityException): ResponseEntity<ErrorResponse> {
        log.error(LOG_ERROR_MESSAGE, e.message, e)

        val errorResponse = ErrorResponse(CONFLICT.value(),e.message!!)
        return ResponseEntity(errorResponse, resolve(errorResponse.statusCode)!!)
    }

}

private const val LOG_ERROR_MESSAGE = "Error exception occurred: {}";
private const val LOG_WARN_MESSAGE = "Warning exception occurred: {}";
private const val LOG_INFO_MESSAGE = "Exception occurred: {}";