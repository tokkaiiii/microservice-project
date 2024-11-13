package com.msa.userservice.config

import graphql.scalars.ExtendedScalars.DateTime
import graphql.schema.idl.RuntimeWiring
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.graphql.execution.RuntimeWiringConfigurer

@Configuration
class GraphQLConfig {

    @Bean
    fun runtimeWritingConfigure(): RuntimeWiringConfigurer {
        return RuntimeWiringConfigurer { wiringBuilder: RuntimeWiring.Builder ->
            wiringBuilder.scalar(DateTime)
        }
    }

}