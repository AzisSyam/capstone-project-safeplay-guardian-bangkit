package com.example.safeplayguardian.di

import android.content.Context
import com.example.safeplayguardian.data.pref.UserPreferences
import com.example.safeplayguardian.data.pref.dataStore
<<<<<<< HEAD
import com.example.safeplayguardian.data.repository.ClassificationRepository
=======
>>>>>>> cc
import com.example.safeplayguardian.data.repository.ToyRepository
import com.example.safeplayguardian.data.repository.UserRepository
import com.example.safeplayguardian.remote.api.ApiConfig
import com.google.firebase.auth.FirebaseAuth

object Injection {
<<<<<<< HEAD
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
=======
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
>>>>>>> cc
      val pref = UserPreferences.getInstance(context.dataStore)
      return UserRepository(pref, firebaseAuth, context)
   }
}