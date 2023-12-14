package com.example.safeplayguardian.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.safeplayguardian.ViewModelFactory
import com.example.safeplayguardian.databinding.ActivityMainBinding
import com.example.safeplayguardian.ui.login.LoginActivity
import com.example.safeplayguardian.ui.profile.ProfileActivity
import com.example.safeplayguardian.ui.recomendation.RecomendationActivity

class MainActivity : AppCompatActivity() {
   private lateinit var binding: ActivityMainBinding
   private lateinit var userPhotoUrl: String
   private lateinit var userId: String

   private val viewModel by viewModels<MainViewModel> {
      ViewModelFactory.getInstance(this)
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityMainBinding.inflate(layoutInflater)
      setContentView(binding.root)

      binding.btnRecomendation.setOnClickListener {
         val intent = Intent(this@MainActivity, RecomendationActivity::class.java)
         startActivity(intent)
      }

      binding.userPhoto.setOnClickListener {
         val intent = Intent(this, ProfileActivity::class.java)
         intent.putExtra("userId", userId)
         startActivity(intent)
      }

//      firebaseAuth = Firebase.auth
//      val firebaseUser = firebaseAuth.currentUser

      viewModel.getSession().observe(this) { user ->
         if (user.userId == "") {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
         }

         userId = user.userId!!

         viewModel.getUserData(userId = userId)

         viewModel.userData.observe(this) { user ->
            val userPhotoUrl = user.photoUrl
            if (userPhotoUrl != null) loadUserProfilePhoto(userPhotoUrl)
         }

         viewModel.error.observe(this){errorMessage->
            Log.d(TAG, "Error fetching user data: $errorMessage")
         }

//         FirebaseManager.getUserData(userId,
//            onSuccess = { user ->
//               userPhotoUrl = user.photoUrl.toString()
//               loadUserProfilePhoto(userPhotoUrl)
//            },
//            onFailure = { exception ->
//               Log.d(TAG, "Error fetching user data: ${exception.message}")
//            }
//         )
      }
   }

   private fun loadUserProfilePhoto(userPhotoUrl: String) {
      if (userPhotoUrl != null) {
         Glide.with(binding.userPhoto)
            .load(userPhotoUrl)
            .circleCrop()
            .into(binding.userPhoto)
      }
   }

   companion object {
      private const val TAG = "MainActivity"
   }
}