package com.example.safeplayguardian.ui.editprofile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.safeplayguardian.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {
   private lateinit var binding: ActivityEditProfileBinding
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityEditProfileBinding.inflate(layoutInflater)
      setContentView(binding.root)

//      appbar
      binding.topAppBar.setNavigationOnClickListener {
         onBackPressed()
      }

   }
}