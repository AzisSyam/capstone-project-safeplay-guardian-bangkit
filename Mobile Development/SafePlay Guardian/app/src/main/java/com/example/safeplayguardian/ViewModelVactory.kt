package com.example.safeplayguardian

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.safeplayguardian.data.pref.Repository
import com.example.safeplayguardian.di.Injection
import com.example.safeplayguardian.ui.recomendation.ToyRecomendationViewModel

class ViewModelVactory(private val repository: Repository) :
   ViewModelProvider.NewInstanceFactory() {
   @Suppress("UNCHECKED_CAST")
   override fun <T : ViewModel> create(modelClass: Class<T>): T {
      return when {
         modelClass.isAssignableFrom(ToyRecomendationViewModel::class.java) -> {
            ToyRecomendationViewModel(repository) as T
         }

         else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
      }
   }

   companion object {
      @JvmStatic
      fun getInstance(context: Context): ViewModelVactory {
         return ViewModelVactory(
            Injection.provideRepository(context),
//            Injection.provideStoryRepository(context)
         )
      }
   }

}