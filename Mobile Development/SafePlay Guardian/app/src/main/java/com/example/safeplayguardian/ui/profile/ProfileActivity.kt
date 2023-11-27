package com.example.safeplayguardian.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.safeplayguardian.R
import com.example.safeplayguardian.databinding.ActivityProfileBinding
import com.example.safeplayguardian.ui.editprofile.EditProfileActivity

class ProfileActivity : AppCompatActivity() {
   private lateinit var binding: ActivityProfileBinding
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityProfileBinding.inflate(layoutInflater)
      setContentView(binding.root)

      menuHandle()
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
            
            else -> false
         }
      }
   }


}