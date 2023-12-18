package com.example.safeplayguardian.ui.login

import android.content.Intent
import android.os.Bundle
<<<<<<< HEAD
import android.util.Log
=======
import android.widget.Toast
>>>>>>> cc
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.safeplayguardian.R
import com.example.safeplayguardian.ViewModelFactory
import com.example.safeplayguardian.databinding.ActivityLoginBinding
import com.example.safeplayguardian.ui.main.MainActivity
import com.example.safeplayguardian.ui.signup.SignUpActivity
import com.example.safeplayguardian.utils.DialogHelper
<<<<<<< HEAD
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseTooManyRequestsException

class LoginActivity : AppCompatActivity() {
   private lateinit var binding: ActivityLoginBinding
=======

class LoginActivity : AppCompatActivity() {
   private lateinit var binding: ActivityLoginBinding
//   private lateinit var firebaseAuth: FirebaseAuth
>>>>>>> cc
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

<<<<<<< HEAD
=======
//      firebaseAuth = FirebaseAuth.getInstance()

>>>>>>> cc
      binding.btnLogin.setOnClickListener {
         val email = binding.etEmail.text.toString()
         val password = binding.etPassword.text.toString()
         if (email.isNotEmpty() && password.isNotEmpty()) {
<<<<<<< HEAD
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
=======
            viewModel.signInWithEmailAndPassword(email, password)
         } else {
            Toast.makeText(this, getString(R.string.form_empty_message), Toast.LENGTH_SHORT).show()
         }

//         val email = binding.etEmail.text.toString()
//         val password = binding.etPassword.text.toString()
//
//
//         if (email.isNotEmpty() && password.isNotEmpty()) {
//            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
//               if (it.isSuccessful) {
//                  val firebaseUser = firebaseAuth.currentUser
//                  val uid = firebaseUser?.uid
//                  Log.d("uid", "$uid")
//
//                  if (uid != null) {
//                     viewModel.saveSession(LoginResult(uid, email))
//                  }
//
//                  val intent = Intent(this, MainActivity::class.java)
//                  startActivity(intent)
//                  finish()
//               } else {
//                  Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
//                  Log.d("Tombol login ditekan", "onCreate: ${it.exception}")
//               }
//            }
//         } else {
//            Toast.makeText(this, getString(R.string.form_empty_message), Toast.LENGTH_SHORT).show()
//         }
      }

      viewModel.loginResult.observe(this) { loginResult ->
         if (loginResult.success) {
            viewModel.saveSession(loginResult)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
         } else {
            Toast.makeText(this, loginResult?.error, Toast.LENGTH_SHORT).show()
>>>>>>> cc
         }
      }

      viewModel.isLoading.observe(this) { isLoading ->
         DialogHelper.showLoading(progressBar = binding.progressBar, isLoading = isLoading)
<<<<<<< HEAD
         binding.btnLogin.isClickable = !isLoading
      }
   }

   companion object {
      const val TAG = "LoginActivity"
   }
}

=======
      }
   }
}
>>>>>>> cc
