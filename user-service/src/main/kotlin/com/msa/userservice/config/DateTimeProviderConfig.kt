package com.msa.userservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.auditing.DateTimeProvider
import java.time.OffsetDateTime
import java.util.*

@Configuration
class DateTimeProviderConfig {

    @Bean
    fun dateTimeProvider(): DateTimeProvider? {
        return DateTimeProvider { Optional.of(OffsetDateTime.now()) }
    }

}