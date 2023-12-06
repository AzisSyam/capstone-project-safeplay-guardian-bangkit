package com.example.safeplayguardian.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safeplayguardian.data.repository.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel()  {
   fun logout() {
      viewModelScope.launch {
         userRepository.logout()
      }
   }
}