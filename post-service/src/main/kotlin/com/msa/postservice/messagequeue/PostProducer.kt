package com.msa.postservice.messagequeue

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.msa.postservice.dto.PostDto
import com.msa.postservice.dto.kafka.Field
import com.msa.postservice.dto.kafka.KafkaPostDto
import com.msa.postservice.dto.kafka.Payload
import com.msa.postservice.dto.kafka.Schema
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.time.ZoneOffset.UTC

private const val TIMESTAMP = "org.apache.kafka.connect.data.Timestamp"
private const val INT64 = "int64"
private const val STRING = "string"
private const val ID = "id"
private const val VERSION1 = 1
private const val CREATED_AT = "created_at"
private const val UPDATED_AT = "updated_at"
private const val CONTENT = "content"
private const val POST_ID = "post_id"
private const val TITLE = "title"
private const val USER_ID = "user_id"
private const val USERNAME = "username"

@Service
class PostProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>
) {
    private final val fields = listOf(
        Field(type = INT64, field = ID),
        Field(type = INT64, name = TIMESTAMP, version = VERSION1, field = CREATED_AT),
        Field(type = INT64, name = TIMESTAMP, version = VERSION1, field = UPDATED_AT),
        Field(type = STRING, field = CONTENT),
        Field(type = STRING, field = POST_ID),
        Field(type = STRING, field = TITLE),
        Field(type = STRING, field = USER_ID),
        Field(type = STRING, field = USERNAME)
    )

    val schema = Schema(fields = fields)

    fun send(topic: String, postDto: PostDto): PostDto {
//        val createdAtMillis = now().toInstant(UTC).toEpochMilli()
        val createdAtMillis = postDto.createdAt!!.toInstant(UTC).toEpochMilli()
        val updatedAtMillis = postDto.updatedAt!!.toInstant(UTC).toEpochMilli()
        val payload = Payload(
            id = postDto.id!!,
            createdAt = createdAtMillis,
            updatedAt = updatedAtMillis,
            content = postDto.content,
            postId = postDto.postId,
            title = postDto.title,
            userId = postDto.userId,
            username = postDto.username
        )
        val kafkaPostDto = KafkaPostDto(schema = schema,payload = payload)
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        val jsonInString = objectMapper.writeValueAsString(kafkaPostDto)
        kafkaTemplate.send(topic, jsonInString)
        return postDto
    }
}
