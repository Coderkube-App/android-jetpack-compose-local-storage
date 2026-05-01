package com.compose.localstorage.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.compose.localstorage.data.local.dao.TaskDao
import com.compose.localstorage.data.local.dao.UserDao
import com.compose.localstorage.data.local.entities.TaskEntity
import com.compose.localstorage.data.local.entities.UserEntity

@Database(entities = [UserEntity::class, TaskEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun taskDao(): TaskDao
}
