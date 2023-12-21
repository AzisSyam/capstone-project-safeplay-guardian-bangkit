package com.example.safeplayguardian.ui

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.safeplayguardian.databinding.ActivityWelcomeBinding
import com.example.safeplayguardian.ui.login.LoginActivity

class WelcomeActivity : AppCompatActivity() {

   private lateinit var binding: ActivityWelcomeBinding

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityWelcomeBinding.inflate(layoutInflater)
      setContentView(binding.root)

      binding.btnStart.setOnClickListener {
         if (isInternetAvailable()) {
            val intent = Intent(this@WelcomeActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
         } else {
            Toast.makeText(this, "Tidak ada internet", Toast.LENGTH_SHORT).show()
         }
      }
   }

//   internet check
   private fun isInternetAvailable(): Boolean {
      val connectivityManager =
         getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

      if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
         val networkCapabilities = connectivityManager.activeNetwork ?: return false
         val activeNetwork =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

         return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            // Untuk API level 23 ke bawah, Anda mungkin perlu menambahkan cek untuk TRANSPORT_ETHERNET dan TRANSPORT_BLUETOOTH
            else -> false
         }
      } else {
         val activeNetworkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
         return activeNetworkInfo != null && activeNetworkInfo.isConnected
      }
   }
}