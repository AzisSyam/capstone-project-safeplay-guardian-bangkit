package com.example.safeplayguardian.utils

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.widget.ProgressBar

fun isNetworkAvailable(context: Context): Boolean {
   val connectivityManager =
      context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
   val networkInfo = connectivityManager.activeNetworkInfo
   return networkInfo != null && networkInfo.isConnected
}

fun showLoading(isLoading: Boolean){

}

object DialogHelper{
   fun showLoading(isLoading: Boolean, progressBar: ProgressBar){
      progressBar.visibility = if (isLoading == true) View.VISIBLE else View.GONE
   }
}

