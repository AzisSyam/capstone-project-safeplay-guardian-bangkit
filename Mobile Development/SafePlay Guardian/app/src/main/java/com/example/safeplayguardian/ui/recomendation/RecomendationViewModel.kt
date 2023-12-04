package com.example.safeplayguardian.ui.recomendation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safeplayguardian.data.pref.Repository
import com.example.safeplayguardian.remote.response.ListToyItem
import com.example.safeplayguardian.remote.response.ToysRecomendationResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RecomendationViewModel(private val repository: Repository) : ViewModel() {
   private val _toyItem = MutableLiveData<List<ListToyItem?>?>()
   val toyItem: LiveData<List<ListToyItem?>?> = _toyItem

   private val _isLoading = MutableLiveData<Boolean>()
   val isLoading: LiveData<Boolean> = _isLoading

   private val _errorResponse = MutableLiveData<String>()
   val errorResponse: LiveData<String> = _errorResponse

   fun getRecomendation() {
      _isLoading.value = true
      viewModelScope.launch {
         try {
            val response = repository.getRecomendation()
            _isLoading.value = false
            _toyItem.value = response.listToy
            Log.d("story detail", response.listToy.toString())
         } catch (e: HttpException) {
            _isLoading.value = false
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ToysRecomendationResponse::class.java)
            _errorResponse.value = errorResponse.message!!
            Log.d("story List", e.message.toString())
         }
      }
   }
}