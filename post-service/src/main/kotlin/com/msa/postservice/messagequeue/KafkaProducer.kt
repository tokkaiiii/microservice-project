package com.msa.postservice.messagequeue

import com.fasterxml.jackson.databind.ObjectMapper
import com.msa.postservice.dto.PostDto
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>
) {

    fun send(topic: String, postDto: PostDto) {
        val objectMapper = ObjectMapper()
        val jsonInString: String = objectMapper.writeValueAsString(postDto)
        kafkaTemplate.send(topic, jsonInString)
    }

}
