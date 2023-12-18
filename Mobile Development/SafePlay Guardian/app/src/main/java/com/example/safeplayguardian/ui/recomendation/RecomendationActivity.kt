package com.example.safeplayguardian.ui.recomendation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.safeplayguardian.R
import com.example.safeplayguardian.ViewModelFactory
import com.example.safeplayguardian.databinding.ActivityRecomendationBinding
import com.example.safeplayguardian.remote.response.ListToyItem
import com.example.safeplayguardian.ui.adapter.ToysAdapter
import com.example.safeplayguardian.utils.isNetworkAvailable
import com.google.android.material.snackbar.Snackbar
import retrofit2.HttpException

class RecomendationActivity : AppCompatActivity() {
   private lateinit var binding: ActivityRecomendationBinding
   private val viewModel: RecomendationViewModel by viewModels {
      ViewModelFactory.getInstance(this)
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityRecomendationBinding.inflate(layoutInflater)
      setContentView(binding.root)

      setupView()
      setupAction()
   }

   private fun setupView() {
      binding.topAppBar.setNavigationOnClickListener {
         onBackPressed()
      }

      binding.rvStories.layoutManager = LinearLayoutManager(this)
   }

   private fun setupAction() {
      getData()
      binding.btnRetry.setOnClickListener {
         getData()
      }
   }

   private fun getData() {
      if (isNetworkAvailable(this)) {
         try {
            viewModel.getRecomendation()
            viewModel.isLoading.observe(this) {
               showLoading(it)
            }
            viewModel.toyItem.observe(this@RecomendationActivity) { data ->
               val adapter = ToysAdapter(data) { selectedToy ->
                  showDetailDialog(selectedToy)
               }
               adapter.submitList(data)
               binding.rvStories.adapter = adapter
            }

            viewModel.errorResponse.observe(this){
               if (it.isNotEmpty()){
                  binding.tvToyNotFound.visibility = View.GONE
                  binding.btnRetry.visibility = View.GONE
               }
            }
         } catch (e: HttpException) {
            Toast.makeText(this, e.message(), Toast.LENGTH_LONG).show()
         }
      } else {
         val contextView = findViewById<View>(R.id.recomendation_activity)
         Snackbar.make(contextView, "Tidak ada internet", Snackbar.LENGTH_SHORT)
            .show()
         showLoading(false)
         binding.tvToyNotFound.visibility = View.VISIBLE
         binding.btnRetry.visibility = View.VISIBLE
      }
   }

   //   dialod fragment detail mainan
   private fun showDetailDialog(toy: ListToyItem) {
      val detailDialog = DetailToyFragment.newInstance(toy)
      detailDialog.show(supportFragmentManager, "rounded_dialog_fragment")
   }

   //   menampilkan progress bar
   private fun showLoading(isLoading: Boolean?) {
      binding.progressHorizontal.visibility = if (isLoading!!) View.VISIBLE else View.GONE
   }
}