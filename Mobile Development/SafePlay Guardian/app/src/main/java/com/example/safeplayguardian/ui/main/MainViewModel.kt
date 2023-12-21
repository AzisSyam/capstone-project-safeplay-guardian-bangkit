package com.example.safeplayguardian.ui.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.safeplayguardian.R
import com.example.safeplayguardian.data.pref.LoginResult
import com.example.safeplayguardian.data.repository.ClassificationRepository
import com.example.safeplayguardian.data.repository.UserRepository
import com.example.safeplayguardian.remote.response.ClassificationResponse
import com.example.safeplayguardian.remote.response.Result
import com.example.safeplayguardian.remote.response.UserResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(
   private val userRepository: UserRepository,
   private val classificationRepository: ClassificationRepository
) : ViewModel() {
   private val _isLoading = MutableLiveData<Boolean>()
   val isLoading: LiveData<Boolean> get() = _isLoading

   private val _errorResponse = MutableLiveData<String>()
   val errorResponse: LiveData<String> get() = _errorResponse

   private val _userData = MutableLiveData<UserResponse?>()
   val userData: LiveData<UserResponse?> get() = _userData

   private val _classificationResponse = MutableLiveData<Result?>()
   val classificationData: LiveData<Result?> get() = _classificationResponse

   fun getUserData(userId: String) {
      userRepository.getUserData(userId)
         .observeForever { user ->
            user?.let {
               _userData.value = it
            } ?: run {
               _errorResponse.value = "Gagal mengambil data"
            }
         }
   }

   fun getSession(): LiveData<LoginResult> {
      return userRepository.getSession().asLiveData()
   }

   fun startClassification(file: MultipartBody.Part, context: Context) {
      _isLoading.value = true

      val client = classificationRepository.startClassification(file)
      client.enqueue(object : Callback<ClassificationResponse> {
         override fun onResponse(
            call: Call<ClassificationResponse>,
            response: Response<ClassificationResponse>
         ) {
            _isLoading.value = false
            if (response.isSuccessful) {
               val responseBody = response.body()
               if (responseBody != null) {
                  _classificationResponse.value = responseBody.result as Result
               }
            } else {
               Log.e(MainActivity.TAG, "onFailure: ${response.message()}")
            }
         }

         override fun onFailure(call: Call<ClassificationResponse>, t: Throwable) {
            Log.d(MainActivity.TAG, "onFailure: ${t.message}")
            _errorResponse.value = context.getString(R.string.classification_failed)
            _isLoading.value = false
         }
      })
   }
}