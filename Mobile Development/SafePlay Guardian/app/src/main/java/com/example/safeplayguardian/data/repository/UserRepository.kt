package com.example.safeplayguardian.data.repository

import com.example.safeplayguardian.data.pref.LoginResult
import com.example.safeplayguardian.data.pref.UserPreferences
import kotlinx.coroutines.flow.Flow

class UserRepository (private val userPreferences: UserPreferences){
   suspend fun saveSession(user: LoginResult){
      userPreferences.saveSession(user)
   }

   fun getSession(): Flow<LoginResult> {
      return userPreferences.getUserSession()
   }

   suspend fun logout() {
      userPreferences.logout()
   }
}