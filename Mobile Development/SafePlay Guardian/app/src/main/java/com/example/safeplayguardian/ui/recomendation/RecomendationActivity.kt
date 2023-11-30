package com.example.safeplayguardian.ui.recomendation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.safeplayguardian.ViewModelVactory
import com.example.safeplayguardian.databinding.ActivityRecomendationBinding
import com.example.safeplayguardian.remote.response.ListToyItem
import com.example.safeplayguardian.ui.adapter.ToysAdapter
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RecomendationActivity : AppCompatActivity() {
   private lateinit var binding: ActivityRecomendationBinding
   private val viewModel: ToyRecomendationViewModel by viewModels {
      ViewModelVactory.getInstance(this)
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityRecomendationBinding.inflate(layoutInflater)
      setContentView(binding.root)

      binding.topAppBar.setNavigationOnClickListener {
         onBackPressed()
      }

      binding.rvStories.layoutManager = LinearLayoutManager(this)

      try {
         //      recyclerView
         lifecycleScope.launch {
            viewModel.getRecomendation()
            viewModel.toyItem.observe(this@RecomendationActivity, { data ->
               val adapter = ToysAdapter(data) { selectedToy ->
                  showDetailDialog(selectedToy)
               }
               adapter.submitList(data)
               binding.rvStories.adapter = adapter
            })
         }
      }catch (e:HttpException){
         Toast.makeText(this, e.message(), Toast.LENGTH_LONG).show()
      }


//      paging 3
//      setupListToy()
   }

   //   dialod fragment detail mainan
   private fun showDetailDialog(toy: ListToyItem) {
      val detailDialog = DetailToyFragment.newInstance(toy)
      detailDialog.show(supportFragmentManager, "ToyDetailDialog")
   }

//   paging 3
   /*
      fun setupListToy() {
         val adapter = ToysAdapter()
         binding.rvStories.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
               adapter.retry()
            }
         )
         viewModel.toyItem.observe(this, {
            adapter.submitData(lifecycle, it)
         })

         viewModel.toyItem.observe(this,{
            Log.d("toyItemData", "toyItem: ${it}")
         })
      }
   */
}