package com.example.safeplayguardian.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.example.safeplayguardian.R
import com.example.safeplayguardian.ViewModelFactory
import com.example.safeplayguardian.databinding.ActivityProfileBinding
import com.example.safeplayguardian.ui.login.LoginActivity
import com.example.safeplayguardian.utils.DialogHelper

class ProfileActivity : AppCompatActivity() {
   private lateinit var binding: ActivityProfileBinding

   private val viewModel by viewModels<ProfileViewModel> {
      ViewModelFactory.getInstance(this)
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityProfileBinding.inflate(layoutInflater)
      setContentView(binding.root)

      val toolbar: Toolbar = binding.topAppBar
      setSupportActionBar(toolbar)
      supportActionBar?.setDisplayHomeAsUpEnabled(true)

      val intent = intent
      val userId = intent.getStringExtra("userId")

      if (userId != null) {
         viewModel.getUserData(userId)
         viewModel.userData.observe(this) { user ->
            setData(user.photoUrl!!, user.name!!, user.email!!)
         }
      }
      viewModel.error.observe(this){errorMessage->
         Log.d(TAG, "Error fetching user data: $errorMessage")
      }

//      binding.btnEditProfile.setOnClickListener {
//         val intent = Intent(this@ProfileActivity, EditProfileActivity::class.java)
//         intent.putExtra("userId", userId)
//         startActivity(intent)
//      }

      binding.btnLogout.setOnClickListener {
         DialogHelper.showAlert(getString(R.string.logout_confirm), this){
            viewModel.logout()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
         }
      }

      viewModel.isLoading.observe(this) { isLoading ->
         DialogHelper.showLoading(progressBar = binding.progressBar, isLoading = isLoading)
      }

//      menuHandle()
   }

   private fun setData(userPhotoUrl: String, name: String, email: String) {
      binding.apply {
         Glide.with(profilePhoto).load(userPhotoUrl).circleCrop()
            .into(profilePhoto)
         tvNameProfile.text = name
         tvEmailProfile.text = email
      }
   }

//   private fun menuHandle() {
//
////      binding.topAppBar.setNavigationOnClickListener {
////         onBackPressed()
////      }
//   }

   companion object {
      const val TAG = "EditProfileActivity"
   }
}