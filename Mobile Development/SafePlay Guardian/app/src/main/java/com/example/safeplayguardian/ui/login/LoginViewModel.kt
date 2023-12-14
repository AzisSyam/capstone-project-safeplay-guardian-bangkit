package com.example.safeplayguardian.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safeplayguardian.data.pref.LoginResult
import com.example.safeplayguardian.data.repository.UserRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

   private val _loginResult = MutableLiveData<LoginResult>()
   val loginResult: LiveData<LoginResult> get() = _loginResult

   private val _isLoading = MutableLiveData<Boolean>()
   val isLoading: LiveData<Boolean> get() = _isLoading

   fun signInWithEmailAndPassword(email: String, password: String) {
      viewModelScope.launch {
         _isLoading.value = true
         try {
            val authResult = userRepository.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user
            val uid = firebaseUser?.uid

            if (uid != null) {
               _loginResult.value = LoginResult(uid, email, success = true)
               _isLoading.value = false
            }
         } catch (e: Exception) {
            // Kesalahan umum
            _loginResult.value = LoginResult(error = e.localizedMessage ?: "Login failed")
         } finally {
            _isLoading.value = false
         }
      }
   }


   fun saveSession(user: LoginResult) {
      viewModelScope.launch {
         userRepository.saveSession(user)
      }
   }
}