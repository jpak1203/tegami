package com.jpakku.tegami.models

import com.squareup.moshi.Json

data class MessageModel(
    @field:Json(name="username") val username: String,
    @field:Json(name="subject") val subject: String,
    @field:Json(name="body") val body: String,

)