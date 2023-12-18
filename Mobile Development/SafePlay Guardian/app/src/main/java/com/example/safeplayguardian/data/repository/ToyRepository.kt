package com.example.safeplayguardian.data.repository

<<<<<<< HEAD
=======
import com.example.safeplayguardian.remote.api.ApiConfig
>>>>>>> cc
import com.example.safeplayguardian.remote.api.ApiService
import com.example.safeplayguardian.remote.response.ToysRecomendationResponse

class ToyRepository(private val apiService: ApiService) {
<<<<<<< HEAD
   suspend fun getRecomendation(): ToysRecomendationResponse {
      return apiService.getRecomendation()
=======

   suspend fun getRecomendation(): ToysRecomendationResponse {
      return ApiConfig.getApiService().getRecomendation()
>>>>>>> cc
   }
}