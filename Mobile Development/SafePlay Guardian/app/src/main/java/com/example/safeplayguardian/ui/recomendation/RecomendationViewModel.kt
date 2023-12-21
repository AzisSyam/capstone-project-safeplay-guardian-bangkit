package com.example.safeplayguardian.ui.recomendation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.safeplayguardian.data.repository.ToyRepository
import com.example.safeplayguardian.remote.response.ListToyItem
import com.example.safeplayguardian.remote.response.ToysRecomendationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecomendationViewModel(private val repository: ToyRepository) : ViewModel() {
   private val _toyItem = MutableLiveData<List<ListToyItem?>?>()
   val toyItem: LiveData<List<ListToyItem?>?> = _toyItem

   private val _isLoading = MutableLiveData<Boolean>()
   val isLoading: LiveData<Boolean> = _isLoading

   private val _errorResponse = MutableLiveData<String>()
   val errorResponse: LiveData<String> = _errorResponse

   fun getRecomendation() {
      _isLoading.value = true
      val client = repository.getRecomendation()
      client.enqueue(object : Callback<ToysRecomendationResponse> {
         override fun onResponse(
            call: Call<ToysRecomendationResponse>,
            response: Response<ToysRecomendationResponse>
         ) {
            _isLoading.value = false
            if (response.isSuccessful) {
               val responseBody = response.body()
               if (responseBody != null) {
                  _toyItem.value = responseBody.listToy
               }
            } else {
               _errorResponse.value = response.message()
               Log.d("Rekomendasi activity", response.message())
            }
         }

         override fun onFailure(call: Call<ToysRecomendationResponse>, t: Throwable) {
            _isLoading.value = false
         }
      })
   }
}