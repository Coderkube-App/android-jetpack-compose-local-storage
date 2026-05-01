package com.compose.localstorage.data.local

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit

@Singleton
class PrefManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs: SharedPreferences = context.getSharedPreferences("TaskPlannerPrefs", Context.MODE_PRIVATE)

    fun saveUserEmail(email: String) {
        prefs.edit { putString("logged_in_user_email", email) }
    }

    fun getUserEmail(): String? {
        return prefs.getString("logged_in_user_email", null)
    }

    fun clearSession() {
        prefs.edit { remove("logged_in_user_email") }
    }
}
