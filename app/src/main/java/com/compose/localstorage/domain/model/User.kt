package com.compose.localstorage.domain.model

import java.util.UUID

data class User(
    val id: String = UUID.randomUUID().toString(),
    val email: String,
    val password: String? = null
)
