package com.example.safeplayguardian.ui.signup

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.safeplayguardian.R
import com.example.safeplayguardian.ViewModelFactory
import com.example.safeplayguardian.databinding.ActivitySignUpBinding
import com.example.safeplayguardian.ui.login.LoginActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SignUpActivity : AppCompatActivity(), OnImageSelectedListener {
   private lateinit var binding: ActivitySignUpBinding

   //   private lateinit var firebaseAuth: FirebaseAuth
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
            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
               var imageUrl: String? = null
               var photoName: String? = null

               viewModel.uploadImageToStorage(currentImageUri)
               viewModel.photoName.observe(this) {
                  photoName = it
               }
               viewModel.imageUrl.observe(this) {
//                  imageUrl = it
                  if (imageUrl != null) {
                     viewModel.performSignUp(
                        imageUrl = imageUrl,
                        photoName = photoName!!,
                        name = name,
                        password = password,
                        email = email
                     )
                     viewModel.signUpResult.observe(this) { signUpResult ->
                        if (signUpResult.success) {
                           val intent = Intent(this, LoginActivity::class.java)
                           startActivity(intent)
                           finish()
                        }
                     }
                  }


               }

//               viewModel.performSignUp(
//                  imageUrl = imageUrl!!,
//                  photoName = photoName!!,
//                  name = name,
//                  password = password,
//                  email = email
//               )


            } else {
               Toast.makeText(this, getString(R.string.form_empty_message), Toast.LENGTH_SHORT)
                  .show()
            }
         } catch (e: Exception) {
            Log.d(TAG, "error: ${e.message}")
         }
      }
   }

//   private fun performSignup(imageUrl: String, photoName: String) {
//      try {
//         firebaseAuth = FirebaseAuth.getInstance()
//
//         val name = binding.etName.text.toString()
//         val email = binding.etEmail.text.toString()
//         val password = binding.etPassword.text.toString()
//
//         if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
//            val user = firebaseAuth.createUserWithEmailAndPassword(email, password)
//
//            user.addOnCompleteListener {
//               if (it.isSuccessful) {
//                  val firebaseUser = it.result?.user
//                  if (firebaseUser != null) {
//                     val db = Firebase.firestore
//                     val user = hashMapOf(
//                        "email" to email,
//                        "name" to name,
//                        "photoUrl" to imageUrl,
//                        "fotoName" to photoName
//                     )
//
//                     db.collection("users").document(firebaseUser.uid)
//                        .set(user)
//                        .addOnSuccessListener {
//                           Log.d(
//                              TAG,
//                              "DocumentSnapshot successfully written!"
//                           )
//                        }
//                        .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
//
//                     val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
//                     startActivity(intent)
//                  } else {
//                     Toast.makeText(this, "Firebase user is null", Toast.LENGTH_SHORT).show()
//                     Log.d(TAG, "Firebase user is null")
//                  }
//
//               } else {
//                  Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
//                  Log.d("Tombol regis ditekan", "onCreate: ${it.exception}")
//
//               }
//            }
//         } else {
//            Toast.makeText(this, getString(R.string.form_empty_message), Toast.LENGTH_SHORT)
//               .show()
//         }
//      } catch (e: Exception) {
//         Log.d("Tombol regis ditekan", "onCreate: ${e.message}")
//      }
//   }
//
//   private fun uploadImageToFirebaseStorage(onSuccess: (String, String) -> Unit) {
//      if (currentImageUri != null) {
//         // Pengguna menyertakan file foto
//         val storageReference = FirebaseStorage.getInstance().reference
//         val imageRef = storageReference.child("profile_images/${System.currentTimeMillis()}.jpg")
//
//         imageRef.putFile(currentImageUri!!)
//            .addOnSuccessListener {
//               // Gambar berhasil diunggah, dapatkan URL gambar
//               imageRef.downloadUrl.addOnSuccessListener { imageUrl ->
//                  val photoName = imageRef.name
//                  onSuccess.invoke(imageUrl.toString(), photoName)
//               }
//            }
//            .addOnFailureListener { exception ->
//               // Gagal mengunggah gambar
//               Toast.makeText(
//                  this,
//                  "Gagal mengunggah gambar ${exception.message}",
//                  Toast.LENGTH_SHORT
//               ).show()
//            }
//      } else {
//         // Pengguna tidak menyertakan file foto, gunakan gambar dari Drawable
//         val storageReference = FirebaseStorage.getInstance().reference
//         val drawable = ContextCompat.getDrawable(this, R.drawable.user)
//         val bitmap = (drawable as BitmapDrawable).bitmap
//
//         val baos = ByteArrayOutputStream()
//         bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
//         val data = baos.toByteArray()
//
//         val imageRef = storageReference.child("profile_images/${System.currentTimeMillis()}.jpg")
//
//         imageRef.putBytes(data)
//            .addOnSuccessListener {
//               // Gambar dari Drawable berhasil diunggah, dapatkan URL gambar
//               imageRef.downloadUrl.addOnSuccessListener { imageUrl ->
//                  val photoName = imageRef.name
//                  onSuccess.invoke(imageUrl.toString(), photoName)
//               }
//            }
//            .addOnFailureListener { exception ->
//               // Gagal mengunggah gambar
//               Toast.makeText(
//                  this,
//                  "Failed to upload image: ${exception.message}",
//                  Toast.LENGTH_SHORT
//               ).show()
//            }
//      }
//   }

   private fun showBottomSheet() {
      val modalBottomSheet = ModalBottomSheet()
      modalBottomSheet.setOnImageSelectedListener(this)
      modalBottomSheet.show(supportFragmentManager, TAG)
   }

   override fun onImageSelected(imageUri: Uri) {
      currentImageUri = imageUri
      showImage()
   }

   private fun showImage() {
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
               val message= "Format email tidak valid"
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
            Toast.makeText(requireContext(), "Kamera dipilih", Toast.LENGTH_SHORT).show()
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

      fun setOnImageSelectedListener(listener: OnImageSelectedListener) {
         onImageSelectedListener = listener
      }
   }

   companion object {
      const val TAG = "SignUpActivity"
   }
}

interface OnImageSelectedListener {
   fun onImageSelected(imageUri: Uri)
}
