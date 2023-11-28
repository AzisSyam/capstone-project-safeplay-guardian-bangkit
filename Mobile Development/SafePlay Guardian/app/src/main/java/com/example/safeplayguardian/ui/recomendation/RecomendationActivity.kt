package com.example.safeplayguardian.ui.recomendation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.safeplayguardian.DetailToyFragment
import com.example.safeplayguardian.databinding.ActivityRecomendationBinding

class RecomendationActivity : AppCompatActivity() {
   private lateinit var binding: ActivityRecomendationBinding
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityRecomendationBinding.inflate(layoutInflater)
      setContentView(binding.root)

      binding.topAppBar.setNavigationOnClickListener {
         onBackPressed()
      }

      binding.btnToLogin.setOnClickListener {
//         val intent = Intent(this@RecomendationActivity, LoginActivity::class.java)
//         startActivity(intent)

//         val fragmentManager = supportFragmentManager
//         val DetailToyFragment = DetailToyFragment()
//         val fragment = fragmentManager.findFragmentByTag(DetailToyFragment::class.java.simpleName)
//         if (fragment !is DetailToyFragment) {
//            Log.d(
//               "MyFlexibleFragment",
//               "Fragment Name :" + DetailToyFragment::class.java.simpleName
//            )
//            fragmentManager
//               .beginTransaction()
//               .add(
//                  R.id.recomendation_activity,
//                  DetailToyFragment,
//                  DetailToyFragment::class.java.simpleName
//               )
//               .commit()
//         }
         val fragmentManager = supportFragmentManager
         val transaction = fragmentManager.beginTransaction()

         // Buat instance dari MyDialogFragment
         val dialogFragment = DetailToyFragment()

         // Tampilkan DialogFragment
         dialogFragment.show(transaction, "my_dialog_fragment")
      }
   }
}