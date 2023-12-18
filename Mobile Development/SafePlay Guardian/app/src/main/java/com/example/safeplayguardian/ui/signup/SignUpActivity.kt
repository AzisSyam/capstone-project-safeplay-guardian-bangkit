package com.example.safeplayguardian.ui.signup

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.safeplayguardian.R
import com.example.safeplayguardian.ViewModelFactory
import com.example.safeplayguardian.databinding.ActivitySignUpBinding
import com.example.safeplayguardian.ui.login.LoginActivity
import com.example.safeplayguardian.utils.DialogHelper
import com.google.android.material.snackbar.Snackbar

class SignUpActivity : AppCompatActivity(), OnImageSelectedListener {
   private lateinit var binding: ActivitySignUpBinding
   private var currentImageUri: Uri? = null

   private val viewModel by viewModels<SignUpViewModel> {
      ViewModelFactory.getInstance(this)
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivitySignUpBinding.inflate(layoutInflater)
      setContentView(binding.root)

//      photo profile handle
      setupAction()
   }

   private fun setupAction() {
      emailValidation()
      passwordValidation()
      binding.profilePhoto.setOnClickListener {
         showBottomSheet()
      }

      binding.btnCamera.setOnClickListener {
         showBottomSheet()
      }

      binding.btnRegister.setOnClickListener {
         try {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()
            val passwordConfirm = binding.etPasswordConfirm.text.toString()
            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
               if (passwordConfirm == password) {
                  DialogHelper.showAlert(getString(R.string.data_signup_confirm), this) {
                     var imageUrl: String? = null

                     viewModel.uploadImageToStorage(currentImageUri)
                     viewModel.imageUrl.observe(this) {
                        imageUrl = it
                     }

                     viewModel.photoName.observe(this) {
                        viewModel.performSignUp(
                           imageUrl = imageUrl!!,
                           photoName = it,
                           name = name,
                           password = password,
                           email = email
                        )
                     }

                     viewModel.signUpResult.observe(this) { signUpResult ->
                        if (signUpResult.success) {
                           val intent = Intent(this, LoginActivity::class.java)
                           startActivity(intent)
                           finish()
                        }
                     }
                  }
               } else {
                  Snackbar.make(
                     binding.signupLayout,
                     R.string.password_does_not_match,
                     Snackbar.LENGTH_LONG
                  ).show()
               }
            } else {
               Snackbar.make(
                  binding.signupLayout,
                  R.string.form_empty_message,
                  Snackbar.LENGTH_LONG
               ).show()
            }
         } catch (e: Exception) {
            Log.d(TAG, "error: ${e.message}")
         }

      }

      viewModel.isLoading.observe(this) { isLoading ->
         DialogHelper.showLoading(progressBar = binding.progressBar, isLoading = isLoading)
         binding.btnRegister.isClickable = !isLoading
      }
   }

   private fun showBottomSheet() {
      val modalBottomSheet = ModalBottomSheet()
      modalBottomSheet.setOnImageSelectedListener(this)
      modalBottomSheet.show(supportFragmentManager, TAG)
   }

   override fun onImageSelected(imageUri: Uri) {
      currentImageUri = imageUri
      showImage()
   }

   fun showImage() {
      currentImageUri?.let {
         Log.d("Image URI", "showImage: $it")
         binding.profilePhoto.setImageURI(it)
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
               val message = "Format email tidak valid"
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
               val message: String = this@SignUpActivity.getString(R.string.password_length_alert)
               binding.etPassword.setError(message, null)
            }
         }
      })
   }

   // fragment upload photo profile
   /*
      class ModalBottomSheet : BottomSheetDialogFragment() {
         private var currentImageUri: Uri? = null
         private var onImageSelectedListener: OnImageSelectedListener? = null
         private lateinit var signUpActivity: SignUpActivity

         override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
         ): View? {
            val rootView = inflater.inflate(R.layout.modal_bottom_sheet_content, container, false)
            val btnCamera: Button = rootView.findViewById(R.id.btn_camera_bottom_sheet)
            val btnGallery: Button = rootView.findViewById(R.id.btn_galeri_bottom_sheet)

            signUpActivity = SignUpActivity()

            // Tambahkan onClickListener untuk tombol-tombol di Bottom Sheet
            btnCamera.setOnClickListener {
               // Tambahkan logika untuk aksi kamera
               startCamera()
            }

            btnGallery.setOnClickListener {
               // Tambahkan logika untuk aksi galeri
               startGallery()
            }

            return rootView
         }

         //gallery
         private fun startGallery() {
            launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
         }

         private fun startCamera() {
            try {
               currentImageUri = getImageUri(requireContext())
               launcherIntentCamera.launch(currentImageUri)
            } catch (e: Exception) {
               Log.d(TAG, "startCamera: ${e.message}")
            }
         }

         private val launcherGallery = registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
         ) { uri: Uri? ->
            if (uri != null) {
               currentImageUri = uri
               signUpActivity.showImage()
               onImageSelectedListener?.onImageSelected(currentImageUri!!)
            } else {
               Log.d("Photo Picker", "No media selected")
            }
         }

         private val launcherIntentCamera = registerForActivityResult(
            ActivityResultContracts.TakePicture()
         ) { isSuccess ->
            if (isSuccess) {
               signUpActivity.showImage()
               onImageSelectedListener?.onImageSelected(currentImageUri!!)
            }
         }

         fun setOnImageSelectedListener(listener: OnImageSelectedListener) {
            onImageSelectedListener = listener
         }
      }
   */

   companion object {
      const val TAG = "SignUpActivity"
   }
}
