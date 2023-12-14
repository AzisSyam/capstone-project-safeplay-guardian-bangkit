package com.example.safeplayguardian.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safeplayguardian.data.repository.UserRepository
import com.example.safeplayguardian.remote.response.UserResponse
import kotlinx.coroutines.launch

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

   private val _userData = MutableLiveData<UserResponse>()
   val userData: LiveData<UserResponse> get() = _userData

   private val _error = MutableLiveData<String>()
   val error: LiveData<String> get() = _error

   private val _isLoading = MutableLiveData<Boolean>()
   val isLoading: LiveData<Boolean> get() = _isLoading



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

   fun logout() {
      _isLoading.value = true
      viewModelScope.launch {
         userRepository.logout()
         _isLoading.value = false
      }
   }
}