package com.example.safeplayguardian.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.safeplayguardian.databinding.ActivityMainBinding
import com.example.safeplayguardian.remote.response.UserResponse
import com.example.safeplayguardian.ui.login.LoginActivity
import com.example.safeplayguardian.ui.profile.ProfileActivity
import com.example.safeplayguardian.ui.recomendation.RecomendationActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
   private lateinit var binding: ActivityMainBinding
   private lateinit var firebaseAuth: FirebaseAuth
   private lateinit var userPhotoUrl: String

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
         startActivity(intent)
      }

      firebaseAuth = Firebase.auth
      val firebaseUser = firebaseAuth.currentUser

//      mengambil session
      if (firebaseUser == null) {
         // Not signed in, launch the Login activity
         startActivity(Intent(this, LoginActivity::class.java))
         finish()
         return
      }

//      mengambil data dari firebase dan update beberapa UI
      val db = Firebase.firestore
      val docRef = db.collection("users").document(firebaseUser.uid)
      docRef.get()
         .addOnSuccessListener { document ->
            if (document.exists()) {
               val user = document.toObject(UserResponse::class.java)
               if (user != null) {
                  userPhotoUrl = user.photoUrl.toString()
               }

//               mengatur foto profil pengguna menggunakan data dari firebase
               Glide.with(binding.userPhoto).load(userPhotoUrl).circleCrop()
                  .into(binding.userPhoto)
            } else {
               Log.d(TAG, "No such document")
            }
         }
         .addOnFailureListener { exception ->
            Log.d(TAG, "get failed with ", exception)
         }
   }

   companion object {
      private const val TAG = "MainActivity"
   }
}