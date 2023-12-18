package com.example.safeplayguardian.di

import android.content.Context
import com.example.safeplayguardian.data.pref.UserPreferences
import com.example.safeplayguardian.data.pref.dataStore
import com.example.safeplayguardian.data.repository.ToyRepository
import com.example.safeplayguardian.data.repository.UserRepository
import com.example.safeplayguardian.remote.api.ApiConfig
import com.google.firebase.auth.FirebaseAuth

object Injection {
   fun provideToyRepository(context: Context): ToyRepository {
//      val pref = UserPreference.getInstance(context.dataStore)
//      val user = runBlocking { pref.getUserSession().first() }
      val apiService = ApiConfig.getApiService()
      return ToyRepository(apiService)
   }

   fun provideUserRepository(context: Context): UserRepository {
      val firebaseAuth = FirebaseAuth.getInstance()
//      val pref = UserPreference.getInstance(context.dataStore)
//      val user = runBlocking { pref.getUserSession().first() }
      val pref = UserPreferences.getInstance(context.dataStore)
      return UserRepository(pref, firebaseAuth, context)
   }
}