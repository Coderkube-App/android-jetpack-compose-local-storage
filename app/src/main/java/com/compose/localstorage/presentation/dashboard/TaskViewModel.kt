package com.compose.localstorage.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.localstorage.domain.model.TaskItem
import com.compose.localstorage.domain.repository.TaskRepository
import com.compose.localstorage.presentation.auth.AuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val authManager: AuthManager
) : ViewModel() {

    private val _tasks = MutableStateFlow<List<TaskItem>>(emptyList())
    val tasks: StateFlow<List<TaskItem>> = _tasks.asStateFlow()

    val totalTasks = tasks.map { it.size }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)
    
    val pendingTasks = tasks.map { it.count { task -> !task.isCompleted } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)
    
    val completedTasks = tasks.map { it.count { task -> task.isCompleted } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    init {
        viewModelScope.launch {
            authManager.currentUser.collect { user ->
                user?.let {
                    fetchTasks(it.email)
                }
            }
        }
    }

    private fun fetchTasks(email: String) {
        viewModelScope.launch {
            taskRepository.getTasksByOwner(email).collect {
                _tasks.value = it
            }
        }
    }

    fun addTask(title: String, details: String?, priority: String, dueDate: Long) {
        val user = authManager.currentUser.value ?: return
        val newTask = TaskItem(
            title = title,
            details = details,
            priority = priority,
            dueDate = dueDate,
            ownerEmail = user.email
        )
        viewModelScope.launch {
            taskRepository.insertTask(newTask)
        }
    }

    fun toggleCompletion(task: TaskItem) {
        val newStatus = !task.isCompleted
        viewModelScope.launch {
            if (taskRepository.updateTaskCompletion(task.id, newStatus)) {
                if (newStatus) {
                    // Auto-delete after 3.5 seconds if still completed
                    delay(3500)
                    // Check if still exists and still completed (re-fetching not needed as Flow updates)
                    val currentTask = _tasks.value.find { it.id == task.id }
                    if (currentTask != null && currentTask.isCompleted) {
                        deleteTask(currentTask)
                    }
                }
            }
        }
    }

    fun deleteTask(task: TaskItem) {
        viewModelScope.launch {
            taskRepository.deleteTask(task.id)
        }
    }
    
    fun tasksDueToday(): List<TaskItem> {
        val today = System.currentTimeMillis() // Simplification: should use Calendar for proper date comparison
        return _tasks.value.filter { isSameDay(it.dueDate, today) }
    }
    
    private fun isSameDay(t1: Long, t2: Long): Boolean {
        val c1 = java.util.Calendar.getInstance().apply { timeInMillis = t1 }
        val c2 = java.util.Calendar.getInstance().apply { timeInMillis = t2 }
        return c1.get(java.util.Calendar.YEAR) == c2.get(java.util.Calendar.YEAR) &&
               c1.get(java.util.Calendar.DAY_OF_YEAR) == c2.get(java.util.Calendar.DAY_OF_YEAR)
    }
}
