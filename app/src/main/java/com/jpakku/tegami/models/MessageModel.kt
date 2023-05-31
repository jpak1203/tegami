package com.jpakku.tegami.models

import com.squareup.moshi.Json

data class MessageModel(
    @field:Json(name = "subject") val subject: String?,
    @field:Json(name = "body") val body: String?,
    @field:Json(name = "read") val read: Boolean,
    @field:Json(name = "timestamp") val timestamp: Long
)