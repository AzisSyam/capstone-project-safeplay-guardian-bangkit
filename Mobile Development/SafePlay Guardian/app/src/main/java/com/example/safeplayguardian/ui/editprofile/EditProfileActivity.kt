package com.example.safeplayguardian.ui.editprofile

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.safeplayguardian.R
import com.example.safeplayguardian.databinding.ActivityEditProfileBinding
import com.example.safeplayguardian.ui.profile.ProfileActivity
<<<<<<< HEAD
import com.example.safeplayguardian.ui.signup.OnImageSelectedListener
=======
>>>>>>> cc
import com.example.safeplayguardian.ui.signup.SignUpActivity
import com.example.safeplayguardian.utils.FirebaseManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class EditProfileActivity : AppCompatActivity(), OnImageSelectedListener {
   private lateinit var binding: ActivityEditProfileBinding
   private var currentImageUri: Uri? = null
   private lateinit var firebaseAuth: FirebaseAuth
   private lateinit var userId: String
   private lateinit var photoName: String

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityEditProfileBinding.inflate(layoutInflater)
      setContentView(binding.root)

      val intent = intent
<<<<<<< HEAD
=======
//      val user = intent.getStringExtra("userId")
>>>>>>> cc
      userId = intent.getStringExtra("userId")!!

      if (userId != null) {
         FirebaseManager.getUserData(userId,
            onSuccess = { user ->
               setData(user.photoUrl!!, user.name!!)
               photoName = user.fotoName!!
            },
            onFailure = { exception ->
               Log.d(ProfileActivity.TAG, "Error fetching user data: ${exception.message}")
            }
         )
      }

//      appbar
      binding.topAppBar.setNavigationOnClickListener {
         onBackPressed()
      }

      binding.btnSave.setOnClickListener {
         uploadImageToFirebaseStorage({ imageUrl ->
            performUpdateProfile(imageUrl, userId!!, photoName)
         }, photoName)
      }

      binding.profilePhoto.setOnClickListener {
         showBottomSheet()
      }

      binding.btnCamera.setOnClickListener {
         showBottomSheet()
      }
   }

   private fun setData(userPhotoUrl: String, name: String) {
      binding.apply {
         Glide.with(profilePhoto).load(userPhotoUrl).circleCrop()
            .into(profilePhoto)
         val editableName = Editable.Factory.getInstance().newEditable(name)
         etName.text = editableName
      }
   }

<<<<<<< HEAD
   private fun performUpdateProfile(imageUrl: String , firebaseUser: String, photoName: String) {
=======
   private fun performUpdateProfile(imageUrl: String, firebaseUser: String, photoName: String) {
>>>>>>> cc
      try {
         firebaseAuth = FirebaseAuth.getInstance()

         val newName = binding.etName.text.toString()

         if (newName.isNotEmpty()) {
            val db = Firebase.firestore
            val user = hashMapOf(
               "name" to newName,
               "photoUrl" to imageUrl,
               "fotoName" to photoName
            )

            db.collection("users").document(firebaseUser)
               .update(user as Map<String, Any>)
               .addOnSuccessListener {
                  Log.d(
                     TAG,
                     "DocumentSnapshot successfully written!"
                  )
               }
               .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
         } else {
            Toast.makeText(this, getString(R.string.form_empty_message), Toast.LENGTH_SHORT)
               .show()
         }
      } catch (e: Exception) {
         Log.d("Tombol regis ditekan", "onCreate: ${e.message}")
      }
   }

   private fun uploadImageToFirebaseStorage(onSuccess: (String) -> Unit, photoName: String) {
//      currentImageUri?.let { uri ->
//         val storageReference = FirebaseStorage.getInstance().reference
//         val imageRef = storageReference.child("profile_images/${photoName}")
//
//         imageRef.putFile(uri)
//            .addOnSuccessListener {
//               // Gambar berhasil diunggah, dapatkan URL gambar
//               imageRef.downloadUrl.addOnSuccessListener { imageUrl ->
//                  onSuccess.invoke(imageUrl.toString())
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

      if (currentImageUri != null) {
         // Pengguna menyertakan file foto
         val storageReference = FirebaseStorage.getInstance().reference
         val imageRef = storageReference.child("profile_images/${photoName}")

         imageRef.putFile(currentImageUri!!)
            .addOnSuccessListener {
               // Gambar berhasil diunggah, dapatkan URL gambar
               imageRef.downloadUrl.addOnSuccessListener { imageUrl ->
                  onSuccess.invoke(imageUrl.toString())
               }
            }
            .addOnFailureListener { exception ->
               // Gagal mengunggah gambar
               Toast.makeText(
                  this,
                  "Failed to upload image: ${exception.message}",
                  Toast.LENGTH_SHORT
               ).show()
            }
      } else {
         // Pengguna tidak menyertakan file foto, gunakan gambar dari Drawable
         val storageReference = FirebaseStorage.getInstance().reference
         val drawable = ContextCompat.getDrawable(this, R.drawable.user)
         val bitmap = (drawable as BitmapDrawable).bitmap

         val baos = ByteArrayOutputStream()
         bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
         val data = baos.toByteArray()

         val imageRef = storageReference.child("profile_images/${photoName}")

         imageRef.putBytes(data)
            .addOnSuccessListener {
               // Gambar dari Drawable berhasil diunggah, dapatkan URL gambar
               imageRef.downloadUrl.addOnSuccessListener { imageUrl ->
                  onSuccess.invoke(imageUrl.toString())
               }
            }
            .addOnFailureListener { exception ->
               // Gagal mengunggah gambar
               Toast.makeText(
                  this,
                  "Failed to upload image: ${exception.message}",
                  Toast.LENGTH_SHORT
               ).show()
            }
      }
   }

   private fun showBottomSheet() {
      val modalBottomSheet = EditProfileActivity.ModalBottomSheet()
      modalBottomSheet.setOnImageSelectedListener(this)
      modalBottomSheet.show(supportFragmentManager, SignUpActivity.TAG)
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

   class ModalBottomSheet : BottomSheetDialogFragment() {
      private var currentImageUri: Uri? = null
      private var onImageSelectedListener: OnImageSelectedListener? = null
      private lateinit var editProfileActivity: EditProfileActivity


      override fun onCreateView(
         inflater: LayoutInflater,
         container: ViewGroup?,
         savedInstanceState: Bundle?
      ): View? {
         val rootView = inflater.inflate(R.layout.modal_bottom_sheet_content, container, false)
         val btnCamera: Button = rootView.findViewById<Button>(R.id.btn_camera_bottom_sheet)
         val btnGallery: Button = rootView.findViewById(R.id.btn_galeri_bottom_sheet)

         editProfileActivity = EditProfileActivity()

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

      private fun startGallery() {
         launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
      }

      private val launcherGallery = registerForActivityResult(
         ActivityResultContracts.PickVisualMedia()
      ) { uri: Uri? ->
         if (uri != null) {
            currentImageUri = uri
            editProfileActivity.showImage()
            onImageSelectedListener?.onImageSelected(currentImageUri!!)
         } else {
            Log.d("Photo Picker", "No media selected")
         }
      }

      fun setOnImageSelectedListener(listener: OnImageSelectedListener) {
         onImageSelectedListener = listener
      }

      companion object {
         const val TAG = "ModalBottomSheet"
      }
   }

   companion object {
      const val TAG = "EditProfileActivity"
   }
}

<<<<<<< HEAD

=======
interface OnImageSelectedListener {
   fun onImageSelected(imageUri: Uri)
}
>>>>>>> cc

