package com.example.safeplayguardian.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.safeplayguardian.data.pref.LoginResult
import com.example.safeplayguardian.data.repository.UserRepository

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {
   fun getSession(): LiveData<LoginResult> {
      return userRepository.getSession().asLiveData()
   }
}