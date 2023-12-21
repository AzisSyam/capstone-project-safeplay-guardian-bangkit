package com.example.safeplayguardian.data.repository

import com.example.safeplayguardian.remote.api.ApiService
import com.example.safeplayguardian.remote.response.ToysRecomendationResponse
import retrofit2.Call

class ToyRepository(private val apiService: ApiService) {
   fun getRecomendation(): Call<ToysRecomendationResponse> {
      return apiService.getRecomendation()
   }
}