package com.example.safeplayguardian.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.safeplayguardian.R
import com.example.safeplayguardian.ViewModelFactory
import com.example.safeplayguardian.databinding.ActivityLoginBinding
import com.example.safeplayguardian.ui.main.MainActivity
import com.example.safeplayguardian.ui.signup.SignUpActivity
import com.example.safeplayguardian.utils.DialogHelper
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseTooManyRequestsException

class LoginActivity : AppCompatActivity() {
   private lateinit var binding: ActivityLoginBinding
   private val viewModel by viewModels<LoginViewModel> {
      ViewModelFactory.getInstance(this)
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityLoginBinding.inflate(layoutInflater)
      setContentView(binding.root)

      binding.tvToSignup.setOnClickListener {
         val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
         startActivity(intent)
      }

      binding.btnLogin.setOnClickListener {
         val email = binding.etEmail.text.toString()
         val password = binding.etPassword.text.toString()
         if (email.isNotEmpty() && password.isNotEmpty()) {
            try {
               viewModel.signInWithEmailAndPassword(email, password)
               viewModel.loginResult.observe(this) { loginResult ->
                  if (loginResult.success) {
                     viewModel.saveSession(loginResult)
                     val intent = Intent(this, MainActivity::class.java)
                     startActivity(intent)
                     finish()
                  } else {
                     DialogHelper.showAlertWithoutNegativeButton(
                        loginResult?.error.toString(),
                        this
                     )
                  }
               }
            } catch (e: FirebaseTooManyRequestsException) {
               // Tangani kesalahan FirebaseTooManyRequestsException
               Log.d(TAG, "onCreate: ${e.message}")
            } catch (e: Exception) {
               // Tangani kesalahan umum lainnya
               Log.d(TAG, "onCreate: ${e.message}")
            }

         } else {
            Snackbar.make(
               binding.loginView,
               R.string.form_empty_message,
               Snackbar.LENGTH_LONG
            ).show()
         }
      }

      viewModel.isLoading.observe(this) { isLoading ->
         DialogHelper.showLoading(progressBar = binding.progressBar, isLoading = isLoading)
         binding.btnLogin.isClickable = !isLoading
      }
   }

   companion object {
      const val TAG = "LoginActivity"
   }
}

