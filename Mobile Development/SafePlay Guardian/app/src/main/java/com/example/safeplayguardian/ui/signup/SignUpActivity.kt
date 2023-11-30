package com.example.safeplayguardian.ui.signup

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.safeplayguardian.R
import com.example.safeplayguardian.databinding.ActivitySignUpBinding
import com.example.safeplayguardian.ui.login.LoginActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
   private lateinit var binding: ActivitySignUpBinding
   private lateinit var firebaseAuth: FirebaseAuth

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivitySignUpBinding.inflate(layoutInflater)
      setContentView(binding.root)

      firebaseAuth = FirebaseAuth.getInstance()

//      photo profile handle
      binding.profilePhoto.setOnClickListener {
//         showBottomSheet()
         val modalBottomSheet = ModalBottomSheet()
         modalBottomSheet.show(supportFragmentManager, ModalBottomSheet.TAG)
      }

      binding.btnCamera.setOnClickListener {
//         showBottomSheet()
         val modalBottomSheet = ModalBottomSheet()
         modalBottomSheet.show(supportFragmentManager, ModalBottomSheet.TAG)
      }

      emailValidation()
      passwordValidation()

      binding.btnRegister.setOnClickListener {
         try {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
               firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                  if (it.isSuccessful) {
                     val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                     startActivity(intent)
                  } else {
                     Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                  }
               }
            } else {
               Toast.makeText(this, getString(R.string.form_empty_message), Toast.LENGTH_SHORT)
                  .show()
            }
         }  catch (e:Exception){
            Log.d("Tombol regis ditekan", "onCreate: ${e.message}")
         }

      }
   }

   //   email validation
   private fun emailValidation() {
      binding.etEmail.addTextChangedListener(object : TextWatcher {
         override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
         }

         override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
         }

         override fun afterTextChanged(s: Editable?) {
            val email = s.toString()
            if (!isEmailValid(email)) {
//               error = null
               val message: String? = "Format email tidak valid"
               binding.etEmail.setError(message, null)
            }
         }

      })
   }

   private fun isEmailValid(email: String): Boolean {
      return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
   }

   //   password input validation
   private fun passwordValidation() {
      binding.etPassword.addTextChangedListener(object : TextWatcher {
         override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
         }

         override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
         }

         override fun afterTextChanged(s: Editable?) {
            if (s.toString().length < 8) {
               val message: String? = this@SignUpActivity.getString(R.string.password_length_alert)
               binding.etPassword.setError(message)
            }
         }
      })
   }

   // fragment upload photo profile
   class ModalBottomSheet : BottomSheetDialogFragment() {
      override fun onCreateView(
         inflater: LayoutInflater,
         container: ViewGroup?,
         savedInstanceState: Bundle?
      ): View? {
         val rootView = inflater.inflate(R.layout.modal_bottom_sheet_content, container, false)
         val btnCamera: Button = rootView.findViewById<Button>(R.id.btn_camera_bottom_sheet)
         val btnGallery: Button = rootView.findViewById(R.id.btn_galeri_bottom_sheet)

         // Tambahkan onClickListener untuk tombol-tombol di Bottom Sheet
         btnCamera.setOnClickListener {
            // Tambahkan logika untuk aksi kamera
            Toast.makeText(requireContext(), "Kamera dipilih", Toast.LENGTH_SHORT).show()
         }

         btnGallery.setOnClickListener {
            // Tambahkan logika untuk aksi galeri
            Toast.makeText(requireContext(), "Galeri dipilih", Toast.LENGTH_SHORT).show()
         }

         return rootView
      }

      companion object {
         const val TAG = "ModalBottomSheet"
      }
   }
}
