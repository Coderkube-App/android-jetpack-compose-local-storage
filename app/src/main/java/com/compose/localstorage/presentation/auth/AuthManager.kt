package com.compose.localstorage.presentation.auth

import com.compose.localstorage.data.local.PrefManager
import com.compose.localstorage.domain.model.User
import com.compose.localstorage.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthManager @Inject constructor(
    private val authRepository: AuthRepository,
    private val prefManager: PrefManager
) {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _isRestoring = MutableStateFlow(true)
    val isRestoring: StateFlow<Boolean> = _isRestoring.asStateFlow()

    private val _authErrorMessage = MutableStateFlow<String?>(null)
    val authErrorMessage: StateFlow<String?> = _authErrorMessage.asStateFlow()

    suspend fun restoreSession() {
        _isRestoring.value = true
        val savedEmail = prefManager.getUserEmail()
        if (savedEmail != null) {
            val user = authRepository.getUserByEmail(savedEmail)
            if (user != null) {
                _currentUser.value = user
                _isLoggedIn.value = true
            } else {
                logout()
            }
        }
        _isRestoring.value = false
    }

    suspend fun login(email: String, password: String) {
        val trimmedEmail = email.trim()
        val trimmedPassword = password.trim()

        if (trimmedEmail.isEmpty() || trimmedPassword.isEmpty()) {
            _authErrorMessage.value = "Email and password are required."
            return
        }

        if (!isValidEmail(trimmedEmail)) {
            _authErrorMessage.value = "Please enter a valid email address."
            return
        }

        val existingUser = authRepository.getUserByEmail(trimmedEmail)
        if (existingUser != null) {
            if (existingUser.password == trimmedPassword) {
                _currentUser.value = existingUser
                _isLoggedIn.value = true
                _authErrorMessage.value = null
                prefManager.saveUserEmail(trimmedEmail)
            } else if (existingUser.password == null) {
                if (authRepository.updatePassword(trimmedEmail, trimmedPassword)) {
                    val updatedUser = existingUser.copy(password = trimmedPassword)
                    _currentUser.value = updatedUser
                    _isLoggedIn.value = true
                    _authErrorMessage.value = null
                    prefManager.saveUserEmail(trimmedEmail)
                } else {
                    _authErrorMessage.value = "Failed to update password."
                }
            } else {
                _authErrorMessage.value = "Incorrect password."
            }
        } else {
            val newUser = User(email = trimmedEmail, password = trimmedPassword)
            if (authRepository.insertUser(newUser)) {
                _currentUser.value = newUser
                _isLoggedIn.value = true
                _authErrorMessage.value = null
                prefManager.saveUserEmail(trimmedEmail)
            } else {
                _authErrorMessage.value = "Failed to create account. Please try again."
            }
        }
    }

    fun logout() {
        _currentUser.value = null
        _isLoggedIn.value = false
        _authErrorMessage.value = null
        prefManager.clearSession()
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
