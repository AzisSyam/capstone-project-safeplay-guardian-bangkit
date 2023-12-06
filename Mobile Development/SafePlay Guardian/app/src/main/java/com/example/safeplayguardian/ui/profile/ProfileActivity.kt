package com.example.safeplayguardian.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.safeplayguardian.R
import com.example.safeplayguardian.ViewModelFactory
import com.example.safeplayguardian.databinding.ActivityProfileBinding
import com.example.safeplayguardian.ui.editprofile.EditProfileActivity
import com.example.safeplayguardian.ui.login.LoginActivity
import com.example.safeplayguardian.utils.FirebaseManager

class ProfileActivity : AppCompatActivity() {
   private lateinit var binding: ActivityProfileBinding

   private val viewModel by viewModels<ProfileViewModel> {
      ViewModelFactory.getInstance(this)
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityProfileBinding.inflate(layoutInflater)
      setContentView(binding.root)

      val intent = intent
      val userId = intent.getStringExtra("userId")
      if (userId != null) {

         FirebaseManager.getUserData(userId,
            onSuccess = { user ->
               setData(user.photoUrl!!, user.name!!, user.email!!)
            },
            onFailure = { exception ->
               Log.d(TAG, "Error fetching user data: ${exception.message}")
            }
         )
      }
      menuHandle()
   }

   private fun setData(userPhotoUrl: String, name: String, email: String) {
      binding.apply {
         Glide.with(profilePhoto).load(userPhotoUrl).circleCrop()
            .into(profilePhoto)
         tvNameProfile.text = name
         tvEmailProfile.text = email
      }
   }

   private fun menuHandle() {

      binding.topAppBar.setNavigationOnClickListener {
         onBackPressed()
      }

      binding.topAppBar.setOnMenuItemClickListener { menuItem ->
         when (menuItem.itemId) {
            R.id.edit_profil -> {
               val intent = Intent(this@ProfileActivity, EditProfileActivity::class.java)
               startActivity(intent)
               true
            }

            R.id.logout -> {
//               firebaseAuth.signOut()
               viewModel.logout()
               val intent = Intent(this, LoginActivity::class.java)
               intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
               startActivity(intent)
               true
            }

            else -> super.onOptionsItemSelected(menuItem)
         }
      }
   }

   companion object {
      const val TAG = "EditProfileActivity"
   }
}