package com.example.safeplayguardian.remote.api

import com.example.safeplayguardian.remote.response.ToysRecomendationResponse
import retrofit2.http.GET

interface ApiService {

   //   recyclerview
   @GET("recomendation")
   suspend fun getRecomendation(): ToysRecomendationResponse
}