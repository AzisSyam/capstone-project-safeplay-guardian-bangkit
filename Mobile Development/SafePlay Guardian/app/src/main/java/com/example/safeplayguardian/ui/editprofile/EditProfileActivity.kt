package com.example.safeplayguardian.ui.editprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.safeplayguardian.R
import com.example.safeplayguardian.databinding.ActivityEditProfileBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EditProfileActivity : AppCompatActivity() {
   private lateinit var binding: ActivityEditProfileBinding

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityEditProfileBinding.inflate(layoutInflater)
      setContentView(binding.root)

//      appbar
      binding.topAppBar.setNavigationOnClickListener {
         onBackPressed()
      }

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

//      val bottomSheetView = layoutInflater.inflate(R.layout.modal_bottom_sheet_content, null)
//      val bottomSheetDialog = BottomSheetDialog(this)
//      bottomSheetDialog.setContentView(bottomSheetView)
   }

   /*private fun showBottomSheet() {
      val bottomSheetView = layoutInflater.inflate(R.layout.modal_bottom_sheet_content, null)
      val bottomSheetDialog = BottomSheetDialog(this)
      bottomSheetDialog.setContentView(bottomSheetView)

      val btnCamera: Button = bottomSheetView.findViewById(R.id.btn_camera_bottom_sheet)
      val btnGallery: Button = bottomSheetView.findViewById(R.id.btn_galeri_bottom_sheet)

      // Tambahkan onClickListener untuk tombol-tombol di Bottom Sheet
      btnCamera.setOnClickListener {
         // Tambahkan logika untuk aksi kamera
         Toast.makeText(this, "Kamera dipilih", Toast.LENGTH_SHORT).show()
         bottomSheetDialog.dismiss()
      }

      btnGallery.setOnClickListener {
         // Tambahkan logika untuk aksi galeri
         Toast.makeText(this, "Galeri dipilih", Toast.LENGTH_SHORT).show()
         bottomSheetDialog.dismiss()
      }

      bottomSheetDialog.show()
//   }
   }*/
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