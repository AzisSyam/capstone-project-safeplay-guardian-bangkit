package com.example.safeplayguardian.ui.signup

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safeplayguardian.data.pref.SignUpResult
import com.example.safeplayguardian.data.repository.UserRepository
import kotlinx.coroutines.launch

class SignUpViewModel(private val repository: UserRepository) : ViewModel() {
   private val _signUpResult = MutableLiveData<SignUpResult>()
   val signUpResult: LiveData<SignUpResult> get() = _signUpResult

   private val _imageUrl = MutableLiveData<String>()
   val imageUrl: LiveData<String> get() = _imageUrl

   private val _photoName = MutableLiveData<String>()
   val photoName: LiveData<String> get() = _photoName

   fun performSignUp(
      imageUrl: String,
      photoName: String,
      name: String,
      email: String,
      password: String
   ) {
      viewModelScope.launch {
         try {
            repository.performSignup(
               imageUrl = imageUrl,
               photoName = photoName,
               name = name,
               email = email,
               password = password
            )
            _signUpResult.value = SignUpResult(success = true)
         } catch (e: Exception) {
            _signUpResult.value = SignUpResult(error = e.localizedMessage ?: "SignUp failed")
         } finally {
//            _isLoading.value = false
         }
      }
   }

   fun uploadImageToStorage(currentImageUri: Uri?) {
      viewModelScope.launch {
         try {
            val (imageUrl, photoName) = repository.uploadImageToFirebaseStorage(currentImageUri = currentImageUri!!)

            _imageUrl.value = imageUrl
            _photoName.value = photoName

         } catch (e: Exception) {
            Log.d("Create account", "uploadImageToStorage: ${e.message}")
         }
      }
   }
}