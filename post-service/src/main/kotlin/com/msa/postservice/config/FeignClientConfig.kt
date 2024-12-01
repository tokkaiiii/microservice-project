package com.msa.postservice.config

import com.msa.postservice.client.FeignErrorDecoder
import feign.Logger.Level
import feign.Logger.Level.FULL
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(basePackages = ["com.msa.postservice"])
class FeignClientConfig{

    @Bean
    fun feignLoggerLevel(): Level = FULL

    @Bean
    fun getFeignErrorDecode(): FeignErrorDecoder = FeignErrorDecoder()
}