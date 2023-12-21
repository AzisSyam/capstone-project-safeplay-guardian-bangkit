package com.example.safeplayguardian.data.repository

import com.example.safeplayguardian.remote.api.ApiService
import com.example.safeplayguardian.remote.response.ClassificationResponse
import okhttp3.MultipartBody
import retrofit2.Call

class ClassificationRepository(private val apiService: ApiService) {
   fun startClassification(file: MultipartBody.Part): Call<ClassificationResponse> {
      return apiService.startClassification(file)
   }
}