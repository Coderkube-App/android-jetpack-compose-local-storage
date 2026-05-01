package com.compose.localstorage.domain.repository

import com.compose.localstorage.domain.model.TaskItem
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTasksByOwner(email: String): Flow<List<TaskItem>>
    suspend fun insertTask(task: TaskItem): Boolean
    suspend fun updateTaskCompletion(taskId: String, isCompleted: Boolean): Boolean
    suspend fun deleteTask(taskId: String): Boolean
}
