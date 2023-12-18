package com.example.safeplayguardian.data.repository

import com.example.safeplayguardian.remote.api.ApiService
import com.example.safeplayguardian.remote.response.ToysRecomendationResponse

class ToyRepository(private val apiService: ApiService) {
   suspend fun getRecomendation(): ToysRecomendationResponse {
      return apiService.getRecomendation()
   }
}