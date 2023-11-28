package com.example.safeplayguardian.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.safeplayguardian.databinding.ActivityLoginBinding
import com.example.safeplayguardian.ui.signup.SignUpActivity

class LoginActivity : AppCompatActivity() {
   private lateinit var binding: ActivityLoginBinding
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityLoginBinding.inflate(layoutInflater)
      setContentView(binding.root)

      binding.tvToSignup.setOnClickListener {
         val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
         startActivity(intent)
      }
   }
}