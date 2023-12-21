package com.example.safeplayguardian

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.safeplayguardian.data.repository.ClassificationRepository
import com.example.safeplayguardian.data.repository.ToyRepository
import com.example.safeplayguardian.data.repository.UserRepository
import com.example.safeplayguardian.di.Injection
import com.example.safeplayguardian.ui.login.LoginViewModel
import com.example.safeplayguardian.ui.main.MainViewModel
import com.example.safeplayguardian.ui.profile.ProfileViewModel
import com.example.safeplayguardian.ui.recomendation.RecomendationViewModel
import com.example.safeplayguardian.ui.signup.SignUpViewModel

class ViewModelFactory(
   private val toyRepository: ToyRepository,
   private val userRepository: UserRepository,
   private val classificationRepository: ClassificationRepository
) :
   ViewModelProvider.NewInstanceFactory() {
   @Suppress("UNCHECKED_CAST")
   override fun <T : ViewModel> create(modelClass: Class<T>): T {
      return when {
         modelClass.isAssignableFrom(RecomendationViewModel::class.java) -> {
            RecomendationViewModel(toyRepository) as T
         }

         modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
            LoginViewModel(userRepository) as T
         }

         modelClass.isAssignableFrom(MainViewModel::class.java) -> {
            MainViewModel(userRepository, classificationRepository) as T
         }

         modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
            ProfileViewModel(userRepository) as T
         }

         modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
            SignUpViewModel(userRepository) as T
         }

         else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
      }
   }

   companion object {
      @JvmStatic
      fun getInstance(context: Context): ViewModelFactory {
         return ViewModelFactory(
            Injection.provideToyRepository(),
            Injection.provideUserRepository(context),
            Injection.provideClassification()
         )
      }
   }

}