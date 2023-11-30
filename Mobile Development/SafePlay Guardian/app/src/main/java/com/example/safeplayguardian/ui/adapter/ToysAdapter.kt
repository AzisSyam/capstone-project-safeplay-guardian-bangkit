package com.example.safeplayguardian.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.safeplayguardian.databinding.RecomendationItemBinding
import com.example.safeplayguardian.remote.response.ListToyItem


//recyclerview
class ToysAdapter(
   private val toyList: List<ListToyItem?>?,
   private val onItemClick: (ListToyItem) -> Unit
) : ListAdapter<ListToyItem, ToysAdapter.MyViewHolder>(DIFF_CALLBACK) {
   override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
      val data = toyList?.get(position)
      if (data != null) {
         holder.bind(data)
         holder.itemView.setOnClickListener {
            try {
               onItemClick(data)
            } catch (e: Exception) {
               Log.d("recycler click", e.message.toString())
            }
         }
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToysAdapter.MyViewHolder {
      val binding =
         RecomendationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return MyViewHolder(binding)
   }

   class MyViewHolder(private val binding: RecomendationItemBinding) :
      RecyclerView.ViewHolder(binding.root) {
      fun bind(data: ListToyItem) {
         binding.tvToysName.text = data.name
         binding.tvToyDesc.text = data.description
         Glide.with(binding.toyImage).load(data.photoUrl).into(binding.toyImage)
      }
   }

   companion object {
      private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListToyItem>() {
         override fun areItemsTheSame(oldItem: ListToyItem, newItem: ListToyItem): Boolean {
            return oldItem == newItem
         }

         override fun areContentsTheSame(oldItem: ListToyItem, newItem: ListToyItem): Boolean {
            return oldItem == newItem
         }
      }
   }
}
