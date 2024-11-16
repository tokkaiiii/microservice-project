package com.msa.userservice.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.msa.userservice.service.UserService
import com.msa.userservice.vo.LoginRequest
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.env.Environment
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.time.Instant
import java.util.*

class AuthenticationFilter(
    private val authenticationManager: AuthenticationManager,
    private val userService: UserService,
    private val env: Environment
) : UsernamePasswordAuthenticationFilter(authenticationManager) {

    override fun attemptAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?
    ): Authentication {
        val credential = ObjectMapper().readValue(request?.inputStream, LoginRequest::class.java)
        return authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(credential.email, credential.password,
                mutableListOf()
            )
        )
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {

        val username = (authResult?.principal as org.springframework.security.core.userdetails.User ).username
        val userDto = userService.getUserByEmail(username)
        val secretKeyBytes = Base64.getEncoder().encode(env.getProperty("jwt.token.secrete")!!.toByteArray())
            val now = Instant.now()
        val secretKey = Keys.hmacShaKeyFor(secretKeyBytes)


        val expirationTimeMillis = environment.getProperty("token.expiration_time")?.toLongOrNull() ?: 3600000L
        val expirationDate = Date.from(now.plusMillis(expirationTimeMillis))

        val token = Jwts.builder()
            .setSubject(userDto.userId.toString())
            .setExpiration(expirationDate)
            .setIssuedAt(Date.from(now))
            .signWith(secretKey)
            .compact()

        response?.addHeader("token", token.toString())
        response?.addHeader("userId", userDto.userId)
    }
}