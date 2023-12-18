package com.example.safeplayguardian.ui.main

<<<<<<< HEAD
import android.util.Log
=======
>>>>>>> cc
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
<<<<<<< HEAD
import androidx.lifecycle.viewModelScope
import com.example.safeplayguardian.data.pref.LoginResult
import com.example.safeplayguardian.data.repository.ClassificationRepository
import com.example.safeplayguardian.data.repository.UserRepository
import com.example.safeplayguardian.remote.response.Result
import com.example.safeplayguardian.remote.response.ToysRecomendationResponse
import com.example.safeplayguardian.remote.response.UserResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.HttpException
import java.io.IOException

class MainViewModel(
   private val userRepository: UserRepository,
   private val classificationRepository: ClassificationRepository
) : ViewModel() {
   private val _isLoading = MutableLiveData<Boolean>()
   val isLoading: LiveData<Boolean> get() = _isLoading

   private val _errorResponse = MutableLiveData<String>()
   val errorResponse: LiveData<String> get() = _errorResponse

=======
import com.example.safeplayguardian.data.pref.LoginResult
import com.example.safeplayguardian.data.repository.UserRepository
import com.example.safeplayguardian.remote.response.UserResponse

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {
>>>>>>> cc
   private val _userData = MutableLiveData<UserResponse>()
   val userData: LiveData<UserResponse> get() = _userData

   private val _error = MutableLiveData<String>()
   val error: LiveData<String> get() = _error

<<<<<<< HEAD
   private val _classificationResponse = MutableLiveData<Result?>()
   val classificationData: LiveData<Result?> get() = _classificationResponse

=======
>>>>>>> cc
   fun getUserData(userId: String) {
      userRepository.getUserData(userId)
         .observeForever { user ->
            user?.let {
               _userData.value = it
            } ?: run {
               _error.value = "Gagal mengambil data"
            }
         }
   }

   fun getSession(): LiveData<LoginResult> {
      return userRepository.getSession().asLiveData()
   }
<<<<<<< HEAD

   fun startClassification(file: MultipartBody.Part) {
      _isLoading.value = true
      viewModelScope.launch {
         try {
            val response = classificationRepository.startClassification(file)
            _classificationResponse.value = response.result
            Log.d("story detail", response.result.toString())
            _isLoading.value = false
         } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ToysRecomendationResponse::class.java)
            _errorResponse.value = errorResponse.toString()
            Log.d("story List", e.message.toString())
            _isLoading.value = false
         } catch (e:IOException){
            _errorResponse.value = "Gagal mengunggah gambar"
            _isLoading.value = false
         }
      }
   }
=======
>>>>>>> cc
}