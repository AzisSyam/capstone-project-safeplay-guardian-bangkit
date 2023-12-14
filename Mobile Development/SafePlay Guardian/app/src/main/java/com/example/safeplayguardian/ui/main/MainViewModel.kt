package com.example.safeplayguardian.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.safeplayguardian.data.pref.LoginResult
import com.example.safeplayguardian.data.repository.UserRepository
import com.example.safeplayguardian.remote.response.UserResponse

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {
   private val _userData = MutableLiveData<UserResponse>()
   val userData: LiveData<UserResponse> get() = _userData

   private val _error = MutableLiveData<String>()
   val error: LiveData<String> get() = _error

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
}