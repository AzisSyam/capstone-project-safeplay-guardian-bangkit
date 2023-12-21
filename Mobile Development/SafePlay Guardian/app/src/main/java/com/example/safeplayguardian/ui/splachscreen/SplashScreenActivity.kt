package com.example.safeplayguardian.ui.splachscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.safeplayguardian.R
import com.example.safeplayguardian.ui.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {
   // Waktu penundaan (dalam milidetik) sebelum beralih ke layar utama
   private val SPLASH_DELAY: Long = 2000
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_splash_screen)

      Handler().postDelayed({
         // Membuat Intent untuk pindah ke layar utama
         val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
         startActivity(intent)
         finish()
      }, SPLASH_DELAY)
   }
}