package com.example.safeplayguardian.remote.api

<<<<<<< HEAD
import com.example.safeplayguardian.remote.response.ClassificationResponse
import com.example.safeplayguardian.remote.response.ToysRecomendationResponse
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

   @GET("recomendation")
   suspend fun getRecomendation(): ToysRecomendationResponse

   @Multipart
   @POST("predict")
   suspend fun startClassification(
      @Part file: MultipartBody.Part
   ): ClassificationResponse
=======
import com.example.safeplayguardian.remote.response.ToysRecomendationResponse
import retrofit2.http.GET

interface ApiService {

   //   recyclerview
   @GET("recomendation")
   suspend fun getRecomendation(): ToysRecomendationResponse
>>>>>>> cc
}