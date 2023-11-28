package com.example.safeplayguardian

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DetailToyFragment : DialogFragment() {
   private var param1: String? = null
   private var param2: String? = null
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      arguments?.let {
         param1 = it.getString(ARG_PARAM1)
         param2 = it.getString(ARG_PARAM2)
      }
   }

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? {
      // Inflate the layout for this fragment
      val view = inflater.inflate(R.layout.fragment_detail_toy, container, false)

      // Atur aksi klik untuk tombol
      view.findViewById<ImageView>(R.id.btn_close).setOnClickListener {
         Toast.makeText(requireContext(), "Tombol exit ditekan", Toast.LENGTH_SHORT).show()
         dismiss()
      }
      return view
   }

   companion object {
      @JvmStatic
      fun newInstance(param1: String, param2: String) =
         DetailToyFragment().apply {
            arguments = Bundle().apply {
               putString(ARG_PARAM1, param1)
               putString(ARG_PARAM2, param2)
            }
         }
   }
}