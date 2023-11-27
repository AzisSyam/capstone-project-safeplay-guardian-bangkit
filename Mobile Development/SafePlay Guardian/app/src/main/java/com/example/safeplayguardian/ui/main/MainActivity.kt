package com.example.safeplayguardian.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.safeplayguardian.R
import com.example.safeplayguardian.databinding.ActivityMainBinding
import com.example.safeplayguardian.ui.profile.ProfileActivity
import com.example.safeplayguardian.ui.recomendation.RecomendationActivity

class MainActivity : AppCompatActivity() {
   private lateinit var binding: ActivityMainBinding
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityMainBinding.inflate(layoutInflater)
      setContentView(binding.root)

      supportActionBar?.setBackgroundDrawable(resources.getDrawable(R.color.blue_primary))

      binding.btnRecomendation.setOnClickListener {
         val intent = Intent(this@MainActivity, RecomendationActivity::class.java)
         startActivity(intent)
      }

   }

   //   menu
   override fun onCreateOptionsMenu(menu: Menu): Boolean {
      val inflater: MenuInflater = menuInflater
      inflater.inflate(R.menu.main_activity_menu, menu)
      return true
   }

   override fun onOptionsItemSelected(item: MenuItem): Boolean {
      when (item.itemId) {
         R.id.menu_profile -> {
            val intent = Intent(this@MainActivity, ProfileActivity::class.java)
            startActivity(intent)
         }
      }
      return true
   }
}