package com.example.safeplayguardian.ui.profile

import android.content.Intent
import android.os.Bundle
<<<<<<< HEAD
=======
import android.util.Log
>>>>>>> cc
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
<<<<<<< HEAD
import com.example.safeplayguardian.R
=======
>>>>>>> cc
import com.example.safeplayguardian.ViewModelFactory
import com.example.safeplayguardian.databinding.ActivityProfileBinding
import com.example.safeplayguardian.ui.login.LoginActivity
import com.example.safeplayguardian.utils.DialogHelper
<<<<<<< HEAD
import com.google.android.material.snackbar.Snackbar
=======
>>>>>>> cc

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
<<<<<<< HEAD
//      supportActionBar?.setDisplayHomeAsUpEnabled(true)
=======
      supportActionBar?.setDisplayHomeAsUpEnabled(true)
>>>>>>> cc

      val intent = intent
      val userId = intent.getStringExtra("userId")

      if (userId != null) {
         viewModel.getUserData(userId)
         viewModel.userData.observe(this) { user ->
            setData(user.photoUrl!!, user.name!!, user.email!!)
         }
      }
<<<<<<< HEAD
      viewModel.error.observe(this) { errorMessage ->
         Snackbar.make(
            binding.profileView,
            getString(R.string.failed_to_load_data), Snackbar.LENGTH_SHORT
         )
            .show()
      }

      binding.topAppBar.setNavigationOnClickListener {
         // Handle navigation icon press
         onBackPressed()
=======
      viewModel.error.observe(this){errorMessage->
         Log.d(TAG, "Error fetching user data: $errorMessage")
>>>>>>> cc
      }

//      binding.btnEditProfile.setOnClickListener {
//         val intent = Intent(this@ProfileActivity, EditProfileActivity::class.java)
//         intent.putExtra("userId", userId)
//         startActivity(intent)
//      }

      binding.btnLogout.setOnClickListener {
<<<<<<< HEAD
         DialogHelper.showAlert(getString(R.string.logout_confirm), this) {
            viewModel.logout()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
         }
=======
         viewModel.logout()
         val intent = Intent(this, LoginActivity::class.java)
         intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
         startActivity(intent)
>>>>>>> cc
      }

      viewModel.isLoading.observe(this) { isLoading ->
         DialogHelper.showLoading(progressBar = binding.progressBar, isLoading = isLoading)
      }
<<<<<<< HEAD
=======

//      menuHandle()
>>>>>>> cc
   }

   private fun setData(userPhotoUrl: String, name: String, email: String) {
      binding.apply {
         Glide.with(profilePhoto).load(userPhotoUrl).circleCrop()
            .into(profilePhoto)
         tvNameProfile.text = name
         tvEmailProfile.text = email
      }
   }

<<<<<<< HEAD
=======
//   private fun menuHandle() {
//
////      binding.topAppBar.setNavigationOnClickListener {
////         onBackPressed()
////      }
//   }

>>>>>>> cc
   companion object {
      const val TAG = "EditProfileActivity"
   }
}