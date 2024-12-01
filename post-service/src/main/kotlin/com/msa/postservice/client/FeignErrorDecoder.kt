package com.msa.postservice.client

import feign.Response
import feign.codec.ErrorDecoder
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class FeignErrorDecoder : ErrorDecoder {
    override fun decode(methodKey: String?, response: Response?): Exception {
        if (methodKey == null || response == null) {
            return NullPointerException()
        }
         when (response.status()) {
            in 400..499 -> {
                if (methodKey.contains("getUser")) {
                    return ResponseStatusException(
                        HttpStatus.valueOf(response.status()),
                        "user's username is null"
                    )
                }
            }

            in 500..599 -> {

            }

            else -> {
                return Exception(response.reason())
            }
        }
        return Exception(response.reason())
    }
}