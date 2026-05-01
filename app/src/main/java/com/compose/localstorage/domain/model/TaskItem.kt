package com.compose.localstorage.domain.model

import java.util.Date
import java.util.UUID

data class TaskItem(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val details: String? = null,
    val priority: String,
    val dueDate: Long,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val ownerEmail: String
)
