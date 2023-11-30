package com.example.safeplayguardian.data.pref

import com.example.safeplayguardian.remote.api.ApiConfig
import com.example.safeplayguardian.remote.api.ApiService
import com.example.safeplayguardian.remote.response.ToysRecomendationResponse

class Repository(private val apiService: ApiService) {

   suspend fun getRecomendation(): ToysRecomendationResponse {
      return ApiConfig.getApiService().getRecomendation()
   }
}