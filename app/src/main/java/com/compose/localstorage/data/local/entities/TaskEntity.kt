package com.compose.localstorage.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.compose.localstorage.domain.model.TaskItem

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey val id: String,
    val title: String,
    val details: String?,
    val priority: String,
    val dueDate: Long,
    val isCompleted: Boolean,
    val createdAt: Long,
    val ownerEmail: String
)

fun TaskEntity.toDomain(): TaskItem = TaskItem(
    id = id,
    title = title,
    details = details,
    priority = priority,
    dueDate = dueDate,
    isCompleted = isCompleted,
    createdAt = createdAt,
    ownerEmail = ownerEmail
)

fun TaskItem.toEntity(): TaskEntity = TaskEntity(
    id = id,
    title = title,
    details = details,
    priority = priority,
    dueDate = dueDate,
    isCompleted = isCompleted,
    createdAt = createdAt,
    ownerEmail = ownerEmail
)
