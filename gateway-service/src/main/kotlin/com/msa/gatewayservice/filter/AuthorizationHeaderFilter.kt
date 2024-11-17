package com.msa.gatewayservice.filter

import com.msa.gatewayservice.filter.AuthorizationHeaderFilter.Config
import com.msa.gatewayservice.util.logger
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm.HS512
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.core.env.Environment
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.UNAUTHORIZED
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.util.*
import javax.crypto.spec.SecretKeySpec

@Component
class AuthorizationHeaderFilter(
    private val env: Environment
) : AbstractGatewayFilterFactory<Config>(Config::class.java) {
    val log = logger()

    override fun apply(config: Config?): GatewayFilter {
       return GatewayFilter { exchange, chain ->
           val request: ServerHttpRequest = exchange.request
           val response: ServerHttpResponse = exchange.response

           if (!request.headers.containsKey(AUTHORIZATION)){
               return@GatewayFilter onError(exchange, "No authorization header found", UNAUTHORIZED)
           }

           val authorizationHeader = request.headers[AUTHORIZATION]!![0]
           val token = authorizationHeader.removePrefix("Bearer").trim()
           if (!isTokenValid(token)){
               return@GatewayFilter onError(exchange, "Token is not valid", UNAUTHORIZED)
           }
           log.info("Authorization header: $authorizationHeader")

           chain.filter(exchange)
       }
    }

    private fun onError(
        exchange: ServerWebExchange,
        error: String,
        unauthorized: HttpStatus
    ): Mono<Void>? {
        val response: ServerHttpResponse = exchange.response
        response.statusCode = unauthorized
        log.error(error)
        return response.setComplete()
    }

    private fun isTokenValid(token: String): Boolean {
        var isValid = true
        val secretKeyBytes = Base64.getEncoder().encode(env.getProperty("jwt.token.secret")!!.toByteArray())
        val signingKey = SecretKeySpec(secretKeyBytes, HS512.jcaName)

        try {

        val jwtParser = Jwts.parserBuilder()
            .setSigningKey(signingKey)
            .build()?: return false
        val subject = jwtParser.parseClaimsJws(token).body.subject
        if (subject.isBlank()) {
            isValid = false
        }
        }catch (ex: Exception){
            isValid = false
        }
        return isValid
    }



    class Config
}