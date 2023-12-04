package com.example.safeplayguardian.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.safeplayguardian.R
import com.example.safeplayguardian.databinding.ActivityLoginBinding
import com.example.safeplayguardian.ui.main.MainActivity
import com.example.safeplayguardian.ui.signup.SignUpActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
   private lateinit var binding: ActivityLoginBinding
   private lateinit var firebaseAuth: FirebaseAuth
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityLoginBinding.inflate(layoutInflater)
      setContentView(binding.root)

      binding.tvToSignup.setOnClickListener {
         val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
         startActivity(intent)
      }

      firebaseAuth = FirebaseAuth.getInstance()

      binding.btnLogin.setOnClickListener {
         val email = binding.etEmail.text.toString()
         val password = binding.etPassword.text.toString()


         if (email.isNotEmpty() && password.isNotEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
               if (it.isSuccessful) {
                  val intent = Intent(this, MainActivity::class.java)
                  startActivity(intent)
                  finish()
               } else {
                  Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                  Log.d("Tombol login ditekan", "onCreate: ${it.exception}")
               }
            }
         } else {
            Toast.makeText(this, getString(R.string.form_empty_message), Toast.LENGTH_SHORT).show()
         }
      }
   }
}