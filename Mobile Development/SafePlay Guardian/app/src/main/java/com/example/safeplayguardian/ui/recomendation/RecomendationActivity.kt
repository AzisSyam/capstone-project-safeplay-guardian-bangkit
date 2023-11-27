package com.example.safeplayguardian.ui.recomendation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.safeplayguardian.databinding.ActivityRecomendationBinding

class RecomendationActivity : AppCompatActivity() {
   private lateinit var binding: ActivityRecomendationBinding
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityRecomendationBinding.inflate(layoutInflater)
      setContentView(binding.root)

      binding.topAppBar.setNavigationOnClickListener {
         onBackPressed()
      }

   }
}