package com.example.safeplayguardian.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.safeplayguardian.R
import com.example.safeplayguardian.databinding.ActivitySignUpBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SignUpActivity : AppCompatActivity() {
   private lateinit var binding: ActivitySignUpBinding
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivitySignUpBinding.inflate(layoutInflater)
      setContentView(binding.root)

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
   }
}

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