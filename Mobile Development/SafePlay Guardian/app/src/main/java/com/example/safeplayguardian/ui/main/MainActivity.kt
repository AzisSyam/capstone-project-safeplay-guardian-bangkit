package com.example.safeplayguardian.ui.main

import android.content.Intent
<<<<<<< HEAD
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.safeplayguardian.R
import com.example.safeplayguardian.ViewModelFactory
import com.example.safeplayguardian.databinding.ActivityMainBinding
import com.example.safeplayguardian.ui.WelcomeActivity
import com.example.safeplayguardian.ui.profile.ProfileActivity
import com.example.safeplayguardian.ui.recomendation.RecomendationActivity
import com.example.safeplayguardian.utils.DialogHelper
import com.example.safeplayguardian.utils.getImageUri
import com.example.safeplayguardian.utils.uriToFile
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

class MainActivity : AppCompatActivity() {
   private lateinit var binding: ActivityMainBinding
   private lateinit var userId: String
   private var currentImageUri: Uri? = null

=======
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
>>>>>>> cc

   private val viewModel by viewModels<MainViewModel> {
      ViewModelFactory.getInstance(this)
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityMainBinding.inflate(layoutInflater)
      setContentView(binding.root)

<<<<<<< HEAD
      viewModel.getSession().observe(this) { user ->
         if (user.userId == "") {
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
         }

         userId = user.userId!!

         viewModel.getUserData(userId = userId)

         viewModel.userData.observe(this) { user ->
            val userPhotoUrl = user.photoUrl
            if (userPhotoUrl != null) loadUserProfilePhoto(userPhotoUrl)
         }

         viewModel.error.observe(this) { errorMessage ->
            Log.d(TAG, "Error fetching user data: $errorMessage")
         }
      }

=======
>>>>>>> cc
      binding.btnRecomendation.setOnClickListener {
         val intent = Intent(this@MainActivity, RecomendationActivity::class.java)
         startActivity(intent)
      }

      binding.userPhoto.setOnClickListener {
         val intent = Intent(this, ProfileActivity::class.java)
         intent.putExtra("userId", userId)
         startActivity(intent)
      }

<<<<<<< HEAD
      binding.btnCamera.setOnClickListener {
         startCamera()
      }

      binding.btnGallery.setOnClickListener {
         startGallery()
      }

      binding.btnCheck.setOnClickListener {
         currentImageUri.let {
            startClassification()
         }
      }

      viewModel.isLoading.observe(this) {
         showLoading(it)
         binding.btnCheck.isClickable = !it
      }

      viewModel.errorResponse.observe(this) { errorMessage ->
         if (errorMessage.isNotEmpty()) {
            DialogHelper.showAlertWithoutNegativeButton(errorMessage, this)
         }
      }
   }

   private fun showLoading(isLoading: Boolean) {
      if (isLoading) {
         binding.toysImage.alpha = 0.5f
         binding.classificationProgressbar.visibility = View.VISIBLE
      } else {
         binding.toysImage.alpha = 1f
         binding.classificationProgressbar.visibility = View.GONE
=======
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
>>>>>>> cc
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

<<<<<<< HEAD
   private fun startClassification() {
      try {
         currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this)
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val file = MultipartBody.Part.createFormData(
               "file",
               imageFile.name,
               requestImageFile
            )
            viewModel.startClassification(file)
            viewModel.classificationData.observe(this) { result ->
               binding.tvToysName.text = result?.toyName
               binding.tvToysDesc.text = result?.description
            }
         } ?: run {
            Snackbar.make(binding.mainView, getString(R.string.image_not_found), Snackbar.LENGTH_LONG).show()
         }
      } catch (e: Exception) {
         Snackbar.make(binding.mainView, getString(R.string.image_not_found), Snackbar.LENGTH_LONG).show()
         Log.d(TAG, "${e.message}")
      }
   }

   private fun showImage() {
      currentImageUri?.let {
         binding.toysImage.setImageURI(it)
      }
   }

   private fun startCamera() {
      currentImageUri = getImageUri(this)
      launcherIntentCamera.launch(currentImageUri)
   }

   private fun startGallery() {
      launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
   }

   private val launcherGallery = registerForActivityResult(
      ActivityResultContracts.PickVisualMedia()
   ) { uri: Uri? ->
      currentImageUri = uri
      showImage()
   }

   private val launcherIntentCamera = registerForActivityResult(
      ActivityResultContracts.TakePicture()
   ) { isSuccess ->
      if (isSuccess) {
         showImage()
      }
   }

=======
>>>>>>> cc
   companion object {
      private const val TAG = "MainActivity"
   }
}