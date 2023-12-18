package com.example.safeplayguardian.remote.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
   companion object {
<<<<<<< HEAD
      fun getApiService(baseUrl: String): ApiService {
=======
      fun getApiService(): ApiService {
>>>>>>> cc
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
            .build()
         val retrofit = Retrofit.Builder()
<<<<<<< HEAD
            .baseUrl(baseUrl)
=======
            .baseUrl("https://j2wmr.wiremockapi.cloud/")
>>>>>>> cc
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
         return retrofit.create(ApiService::class.java)
      }
   }

}