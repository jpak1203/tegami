package com.jpakku.tegami.models

import com.squareup.moshi.Json

data class UserModel(
    @field:Json(name="email") val email: String,
    @field:Json(name="username") val username: String
)