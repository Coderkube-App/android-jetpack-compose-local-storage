package com.compose.localstorage.domain.repository

import com.compose.localstorage.domain.model.User

interface AuthRepository {
    suspend fun getUserByEmail(email: String): User?
    suspend fun insertUser(user: User): Boolean
    suspend fun updatePassword(email: String, password: String): Boolean
}
