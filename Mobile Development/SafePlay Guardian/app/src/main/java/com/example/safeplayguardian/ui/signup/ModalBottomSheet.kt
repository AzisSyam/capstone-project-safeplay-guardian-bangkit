package com.example.safeplayguardian.ui.signup

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.example.safeplayguardian.R
import com.example.safeplayguardian.utils.getImageUri
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

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

      btnCamera.setOnClickListener {
         startCamera()
      }

      btnGallery.setOnClickListener {
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
         Log.d(SignUpActivity.TAG, "startCamera: ${e.message}")
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

interface OnImageSelectedListener {
   fun onImageSelected(imageUri: Uri)
}
