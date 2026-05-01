package com.compose.localstorage.data.repository

import com.compose.localstorage.data.local.dao.UserDao
import com.compose.localstorage.data.local.entities.toDomain
import com.compose.localstorage.data.local.entities.toEntity
import com.compose.localstorage.domain.model.User
import com.compose.localstorage.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : AuthRepository {
    override suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)?.toDomain()
    }

    override suspend fun insertUser(user: User): Boolean {
        return try {
            userDao.insertUser(user.toEntity())
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun updatePassword(email: String, password: String): Boolean {
        return try {
            userDao.updatePassword(email, password)
            true
        } catch (e: Exception) {
            false
        }
    }
}
