package com.example.safeplayguardian.ui.recomendation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
<<<<<<< HEAD
=======
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
>>>>>>> cc
import com.example.safeplayguardian.R
import com.example.safeplayguardian.remote.response.ListToyItem

// recyclerView
class DetailToyFragment : DialogFragment() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      arguments?.let {

      }
   }

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? {
<<<<<<< HEAD
      val view = inflater.inflate(R.layout.fragment_detail_toy, container, false)
=======
      // Inflate the layout for this fragment
      val view = inflater.inflate(R.layout.fragment_detail_toy, container, true)
>>>>>>> cc

      val toyName = arguments?.getString("toyName")
      val toyDesc = arguments?.getString("toyDesc")
      val toyImage = arguments?.getString("toyImage")

<<<<<<< HEAD
=======
      val requestOptions = RequestOptions().transform(RoundedCorners(16))

>>>>>>> cc
      view.findViewById<TextView>(R.id.tv_toy_name_detail).text = toyName
      view.findViewById<TextView>(R.id.tv_toy_desc_detail).text = toyDesc
      Glide.with(requireContext()).load(toyImage)
         .into(view.findViewById<ImageView>(R.id.toy_image_detail))

      return view
   }

<<<<<<< HEAD
   override fun onStart() {
      super.onStart()
      dialog?.window?.setLayout(
         ViewGroup.LayoutParams.MATCH_PARENT,
         ViewGroup.LayoutParams.WRAP_CONTENT
      )
   }

=======
>>>>>>> cc
   companion object {
      @JvmStatic
      fun newInstance(listToy: ListToyItem): DetailToyFragment {
         val fragment = DetailToyFragment()
         val bundle = Bundle().apply {
            putString("toyName", listToy.name)
            putString("toyImage", listToy.photoUrl)
            putString("toyDesc", listToy.description)
         }
         fragment.arguments = bundle
         return fragment
      }
   }
}