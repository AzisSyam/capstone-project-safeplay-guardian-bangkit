package com.example.safeplayguardian.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.safeplayguardian.R
import com.example.safeplayguardian.databinding.ActivityProfileBinding
import com.example.safeplayguardian.ui.editprofile.EditProfileActivity
import com.example.safeplayguardian.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity() {
   private lateinit var binding: ActivityProfileBinding
   private lateinit var firebaseAuth: FirebaseAuth

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityProfileBinding.inflate(layoutInflater)
      setContentView(binding.root)

      menuHandle()
   }

   private fun menuHandle() {
      firebaseAuth = Firebase.auth

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
               firebaseAuth.signOut()
               val intent = Intent(this, LoginActivity::class.java)
               intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
               startActivity(intent)
               true
            }

            else -> super.onOptionsItemSelected(menuItem)
         }
      }
   }


}