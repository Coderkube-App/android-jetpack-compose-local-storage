package com.compose.localstorage.data.repository

import com.compose.localstorage.data.local.dao.TaskDao
import com.compose.localstorage.data.local.entities.toDomain
import com.compose.localstorage.data.local.entities.toEntity
import com.compose.localstorage.domain.model.TaskItem
import com.compose.localstorage.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {
    override fun getTasksByOwner(email: String): Flow<List<TaskItem>> {
        return taskDao.getTasksByOwner(email).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun insertTask(task: TaskItem): Boolean {
        return try {
            taskDao.insertTask(task.toEntity())
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun updateTaskCompletion(taskId: String, isCompleted: Boolean): Boolean {
        return try {
            taskDao.updateTaskCompletion(taskId, isCompleted)
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun deleteTask(taskId: String): Boolean {
        return try {
            taskDao.deleteTask(taskId)
            true
        } catch (e: Exception) {
            false
        }
    }
}
