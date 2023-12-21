package com.example.safeplayguardian.remote.api

import com.example.safeplayguardian.remote.response.ClassificationResponse
import com.example.safeplayguardian.remote.response.ToysRecomendationResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

   @GET("recomendation")
   fun getRecomendation(): Call<ToysRecomendationResponse>

//   @Multipart
//   @POST("predict")
//   suspend fun startClassification(
//      @Part file: MultipartBody.Part
//   ): ClassificationResponse

   @Multipart
   @POST("predict")
   fun startClassification(
      @Part file: MultipartBody.Part
   ): Call<ClassificationResponse>
}