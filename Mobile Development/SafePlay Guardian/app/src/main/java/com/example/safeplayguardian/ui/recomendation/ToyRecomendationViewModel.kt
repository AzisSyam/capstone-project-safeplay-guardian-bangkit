package com.example.safeplayguardian.ui.recomendation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safeplayguardian.data.pref.Repository
import com.example.safeplayguardian.remote.response.ListToyItem
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ToyRecomendationViewModel(private val repository: Repository) : ViewModel() {
   //   paging 3
//   val toyItem: LiveData<PagingData<ListToyItem>> =
//      repository.getRecomendation().cachedIn(viewModelScope)
   private val _toyItem = MutableLiveData<List<ListToyItem?>?>()
   val toyItem: LiveData<List<ListToyItem?>?> = _toyItem

   fun getRecomendation() {
      viewModelScope.launch {
         try {
            val response = repository.getRecomendation()
            _toyItem.value = response.listToy
            Log.d("story detail", response.listToy.toString())
         } catch (e: HttpException) {
//            _isLoading.value = false
            val errorBody = e.response()?.errorBody()?.string()
//            val errorResponse = Gson().fromJson(errorBody, ToysRecomendationResponse::class.java)
//            _errorResponse.value = errorResponse.message!!
            Log.d("story List", e.message.toString())
         }
      }
   }
}