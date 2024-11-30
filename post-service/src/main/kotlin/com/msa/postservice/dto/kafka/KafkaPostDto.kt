package com.msa.postservice.dto.kafka

data class KafkaPostDto(
    val schema: Schema,
    val payload: Payload
)