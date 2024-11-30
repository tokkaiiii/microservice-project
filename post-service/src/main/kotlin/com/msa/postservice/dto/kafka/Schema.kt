package com.msa.postservice.dto.kafka

data class Schema(
    val type: String = "struct",
    val fields: List<Field>,
    var optional: Boolean = false,
    val name: String = "post",
)