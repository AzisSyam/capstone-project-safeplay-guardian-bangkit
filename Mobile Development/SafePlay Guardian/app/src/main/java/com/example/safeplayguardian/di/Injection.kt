package com.example.safeplayguardian.di

import android.content.Context
import com.example.safeplayguardian.data.pref.Repository
import com.example.safeplayguardian.remote.api.ApiConfig

object Injection {
   fun provideRepository(context: Context): Repository {
//      val pref = UserPreference.getInstance(context.dataStore)
//      val user = runBlocking { pref.getUserSession().first() }
      val apiService = ApiConfig.getApiService()
      return Repository(apiService)
   }
}