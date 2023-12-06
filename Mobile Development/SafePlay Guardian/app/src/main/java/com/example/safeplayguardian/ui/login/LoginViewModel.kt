package com.example.safeplayguardian.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safeplayguardian.data.pref.LoginResult
import com.example.safeplayguardian.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
   fun saveSession(user: LoginResult) {
      viewModelScope.launch {
         userRepository.saveSession(user)
      }
   }
}