package com.msa.gatewayservice.filter

import com.msa.gatewayservice.filter.LoggingFilter.Config
import com.msa.gatewayservice.filter.LoggingFilter.LogLevel.*
import com.msa.gatewayservice.util.logger
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.core.Ordered.HIGHEST_PRECEDENCE
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.time.Instant

@Component
class LoggingFilter : AbstractGatewayFilterFactory<Config>(Config::class.java) {

    private val log = logger()

    override fun apply(config: Config): GatewayFilter {
        val logRequestBody = config.logRequestBody
        val logResponseBody = config.logResponseBody
        val logUri = config.logUri
        val logLevel = config.logLevel

        return OrderedGatewayFilter(
            GatewayFilter { exchange, chain ->
                val request: ServerHttpRequest = exchange.request
                val response: ServerHttpResponse = exchange.response
                val uri = request.uri
                val method = request.method
                val params = request.queryParams
                val startTime = Instant.now()
                val status = response.statusCode
                if (logUri) {
                    logRequest(logLevel,"Request started: URI = $uri, Method = $method, Params = [$params]")
                }
                if (logRequestBody) logRequestBody(request)
                if (logResponseBody) logResponseBody(response)
                return@GatewayFilter chain.filter(exchange).then(
                    Mono.fromRunnable{
                        val elapsedTime = Instant.now().minusMillis(startTime.toEpochMilli()).toEpochMilli()
                        logResponse(logLevel,"Request completed: URI = $uri, Status = $status, Time taken = $elapsedTime ms")
                        if (logResponseBody) logResponseBody(response)
                    }
                )
            }, HIGHEST_PRECEDENCE
        )
    }

    private fun logRequest(logLevel: LogLevel, message: String, vararg args: Any) {
        when (logLevel) {
            DEBUG -> log.debug(message, *args)
            INFO -> log.info(message, *args)
            WARN -> log.warn(message, *args)
            ERROR -> log.error(message, *args)
        }
    }

    private fun logResponse(logLevel: LogLevel, message: String, vararg args: Any) {
        when (logLevel) {
            DEBUG -> log.debug(message, *args)
            INFO -> log.info(message, *args)
            WARN -> log.warn(message, *args)
            ERROR -> log.error(message, *args)
        }
    }

    private fun logRequestBody(request: ServerHttpRequest) {
        log.debug("Request body: {}", request.body)
    }

    private fun logResponseBody(response: ServerHttpResponse) {
        log.debug("Response body: {}", response.toString())
    }

    data class Config(
        var logRequestBody: Boolean = false,
        var logResponseBody: Boolean = false,
        var logUri: Boolean = true,
        var logLevel: LogLevel = INFO
    )

    enum class LogLevel {
        INFO, DEBUG, WARN, ERROR
    }
}
