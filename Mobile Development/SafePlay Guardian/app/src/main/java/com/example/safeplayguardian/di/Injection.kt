package com.example.safeplayguardian.di

import android.content.Context
import com.example.safeplayguardian.data.pref.UserPreferences
import com.example.safeplayguardian.data.pref.dataStore
import com.example.safeplayguardian.data.repository.ClassificationRepository
import com.example.safeplayguardian.data.repository.ToyRepository
import com.example.safeplayguardian.data.repository.UserRepository
import com.example.safeplayguardian.remote.api.ApiConfig
import com.google.firebase.auth.FirebaseAuth

object Injection {
   fun provideToyRepository(): ToyRepository {
      val apiService = ApiConfig.getApiService("https://j2wmr.wiremockapi.cloud/")
      return ToyRepository(apiService)
   }

   fun provideClassification(): ClassificationRepository{
      val apiService = ApiConfig.getApiService("https://toyclassification-fxddnegb2q-uc.a.run.app/")
      return ClassificationRepository(apiService)
   }

   fun provideUserRepository(context: Context): UserRepository {
      val firebaseAuth = FirebaseAuth.getInstance()
      val pref = UserPreferences.getInstance(context.dataStore)
      return UserRepository(pref, firebaseAuth, context)
   }
}