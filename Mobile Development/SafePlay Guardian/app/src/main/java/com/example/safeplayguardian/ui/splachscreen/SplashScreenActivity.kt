package com.example.safeplayguardian.ui.splachscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.safeplayguardian.R
<<<<<<< HEAD
import com.example.safeplayguardian.ui.main.MainActivity
=======
import com.example.safeplayguardian.ui.WelcomeActivity
>>>>>>> cc

class SplashScreenActivity : AppCompatActivity() {
   // Waktu penundaan (dalam milidetik) sebelum beralih ke layar utama
   private val SPLASH_DELAY: Long = 2000 

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_splash_screen)

      Handler().postDelayed({
         // Membuat Intent untuk pindah ke layar utama
<<<<<<< HEAD
         val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
         startActivity(intent)
         finish()
=======
         val intent = Intent(this@SplashScreenActivity, WelcomeActivity::class.java)
         startActivity(intent)
         finish() // Menutup SplashScreenActivity agar tidak dapat kembali ke halaman ini
>>>>>>> cc
      }, SPLASH_DELAY)
   }
}