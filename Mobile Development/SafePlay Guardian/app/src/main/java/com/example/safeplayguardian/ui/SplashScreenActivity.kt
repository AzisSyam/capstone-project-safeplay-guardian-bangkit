package com.example.safeplayguardian.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.safeplayguardian.R

class SplashScreenActivity : AppCompatActivity() {
   // Waktu penundaan (dalam milidetik) sebelum beralih ke layar utama
   private val SPLASH_DELAY: Long = 3000 // 3 detik

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_splash_screen)

      Handler().postDelayed({
         // Membuat Intent untuk pindah ke layar utama
         val intent = Intent(this@SplashScreenActivity, WelcomeActivity::class.java)
         startActivity(intent)
         finish() // Menutup SplashScreenActivity agar tidak dapat kembali ke halaman ini
      }, SPLASH_DELAY)
   }
}