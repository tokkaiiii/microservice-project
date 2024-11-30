package com.msa.postservice.dto.kafka

data class Field(
    val type: String,
    var optional: Boolean = false,
    val name: String? = null,
    val version: Int? = null,
    val field: String
)