package com.example.safeplayguardian.remote.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiConfig {
   companion object {
      fun getApiService(baseUrl: String): ApiService {
         val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
         val authInterceptor = Interceptor { chain ->
            val req = chain.request()
            val requestHeaders = req.newBuilder()
               .build()
            chain.proceed(requestHeaders)
         }
         val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS) // Connection timeout
            .readTimeout(20, TimeUnit.SECONDS)    // Read timeout
            .writeTimeout(15, TimeUnit.SECONDS)   // Write timeout
            .build()
         val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
         return retrofit.create(ApiService::class.java)
      }
   }

}