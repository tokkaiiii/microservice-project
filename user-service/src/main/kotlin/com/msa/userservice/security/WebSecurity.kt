package com.msa.userservice.security

import com.msa.userservice.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authorization.AuthorizationDecision
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.intercept.RequestAuthorizationContext
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.IpAddressMatcher
import java.util.function.Supplier

@Configuration
@EnableWebSecurity
class WebSecurity(
    private val userService: UserService,
    private val env: Environment,
    private val passwordEncoder: PasswordEncoder
) {

    @Bean
    fun configure(http: HttpSecurity): SecurityFilterChain? {
        val authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder::class.java)
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder)
        val authenticationManager = authenticationManagerBuilder.build()
        http.csrf{it.disable()}
            .authorizeHttpRequests{
                it.requestMatchers(AntPathRequestMatcher("/**")).permitAll()
//                it.requestMatchers(AntPathRequestMatcher("/users","POST")).permitAll()
//                  .requestMatchers(AntPathRequestMatcher("/login","POST")).permitAll()
                    .requestMatchers(AntPathRequestMatcher("/actuator/**")).permitAll()
                    .requestMatchers("/**").access{
                        authentication, context -> hasIpAddress(authentication,context)
                    }.anyRequest().authenticated()
            }
            .authenticationManager(authenticationManager)
            .sessionManagement{it.sessionCreationPolicy(STATELESS)}
        http.addFilter(getAuthenticationFilter(authenticationManager))
        http.headers{ it -> it.frameOptions{it.sameOrigin()}}
        return http.build()
    }


    fun hasIpAddress(authenticationSupplier: Supplier<Authentication>, context: RequestAuthorizationContext): AuthorizationDecision {
        val authentication = authenticationSupplier.get()

        val matches = ALLOWED_IP_ADDRESS_MATCHER.matches(context.request)
        return AuthorizationDecision(matches)
    }


    private fun getAuthenticationFilter(authenticationManager: AuthenticationManager): AuthenticationFilter? {
        return AuthenticationFilter(authenticationManager, userService, env)
    }


}

private const val ALLOWED_IP_ADDRESS = "127.0.0.1"
private const val SUBNET = "/16"
private val ALLOWED_IP_ADDRESS_MATCHER = IpAddressMatcher(ALLOWED_IP_ADDRESS+ SUBNET)