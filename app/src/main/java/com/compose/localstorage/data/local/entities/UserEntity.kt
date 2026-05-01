package com.compose.localstorage.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.compose.localstorage.domain.model.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val email: String,
    val password: String?
)

fun UserEntity.toDomain(): User = User(id, email, password)
fun User.toEntity(): UserEntity = UserEntity(id, email, password)
