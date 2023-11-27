package com.example.safeplayguardian.ui.recomendation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.safeplayguardian.databinding.ActivityRecomendationBinding
import com.example.safeplayguardian.ui.login.LoginActivity

class RecomendationActivity : AppCompatActivity() {
   private lateinit var binding: ActivityRecomendationBinding
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityRecomendationBinding.inflate(layoutInflater)
      setContentView(binding.root)

      binding.topAppBar.setNavigationOnClickListener {
         onBackPressed()
      }

      binding.btnToLogin.setOnClickListener {
         val intent = Intent(this@RecomendationActivity, LoginActivity::class.java)
         startActivity(intent)
      }

   }
}