package com.compose.localstorage.di

import com.compose.localstorage.data.repository.AuthRepositoryImpl
import com.compose.localstorage.data.repository.TaskRepositoryImpl
import com.compose.localstorage.domain.repository.AuthRepository
import com.compose.localstorage.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindTaskRepository(
        taskRepositoryImpl: TaskRepositoryImpl
    ): TaskRepository
}
